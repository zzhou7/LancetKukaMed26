package com.kuka.fri.lbr.example;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import com.kuka.device.RoboticArm;
import com.kuka.fri.FRIConfiguration;
import com.kuka.fri.FRISession;
import com.kuka.io.IIoDefinitionProvider;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.applicationModel.tasks.IRoboticsAPITaskInjectableTypes;
import com.kuka.task.RoboticsAPITask;
import com.kuka.threading.ThreadUtil;

/**
 * Implementation of a robot application.
 * <p>
 * The application provides a {@link RoboticsAPITask#initialize()} and a
 * {@link RoboticsAPITask#run()} method, which will be called successively in
 * the application lifecycle. The application will terminate automatically after
 * the {@link RoboticsAPITask#run()} method has finished or after stopping the
 * task. The {@link RoboticsAPITask#dispose()} method will be called, even if an
 * exception is thrown during initialization or run.
 * <p>
 * <b>It is imperative to call <code>super.dispose()</code> when overriding the
 * {@link RoboticsAPITask#dispose()} method.</b>
 * 
 * @see IRoboticsAPITaskInjectableTypes Types and Services available for Dependency Injection
 */
public class FRIIOApp extends RoboticsAPIApplication
{
    private String _clientName;
    private FRISession _friSession;

    @Inject
    private IIoDefinitionProvider _ioDefinitionProvider;

    @Inject
    private RoboticArm _robot;

    @Inject
    private FRIIOGroup _friGroup;

    @Override
    public void initialize()
    {
        // **********************************************************************
        // *** change next line to the FRIClient's IP address                 ***
        // **********************************************************************
        _clientName = "127.0.0.1";
    }

    @Override
    public void dispose()
    {
        getLogger().info("Close connection to client");

        if (null != _friSession)
        {
            _friSession.close();
        }
    }

    @Override
    public void run()
    {
        FRIConfiguration friConfiguration = FRIConfiguration.createRemoteConfiguration(_robot, _clientName);
        friConfiguration.setSendPeriodMilliSec(10);

        friConfiguration.registerIO(_ioDefinitionProvider.getIoDefinition(FRIIOGroup.NAME, "In_Bool_Clock_Enabled"));
        friConfiguration.registerIO(_ioDefinitionProvider.getIoDefinition(FRIIOGroup.NAME, "Out_Bool_Enable_Clock"));
        friConfiguration.registerIO(_ioDefinitionProvider.getIoDefinition(FRIIOGroup.NAME, "Out_Integer_Seconds"));
        friConfiguration.registerIO(_ioDefinitionProvider.getIoDefinition(FRIIOGroup.NAME, "Out_Analog_Deci_Seconds"));

        getLogger().info("Creating FRI connection to " + friConfiguration.getHostName());
        getLogger().info("SendPeriod: " + friConfiguration.getSendPeriodMilliSec() + "ms |"
                + " ReceiveMultiplier: " + friConfiguration.getReceiveMultiplier());

        _friSession = new FRISession(friConfiguration);

        // wait until FRI session is ready to switch to command mode
        try
        {
            _friSession.await(20, TimeUnit.SECONDS);
            getLogger().info("Connection to Client established");
        }
        catch (final TimeoutException e)
        {
            getLogger().error(e.getLocalizedMessage());
            _friSession.close();
            return;
        }

        getLogger().info("enable clock");
        ThreadUtil.milliSleep(5000);
        _friGroup.setOut_Bool_Enable_Clock(true);

        getLogger().info("do something ...");
        ThreadUtil.milliSleep(10000);

        getLogger().info("disable clock");
        _friGroup.setOut_Bool_Enable_Clock(false);

        _robot.getFlange().move(ptpHome());
    }
}
