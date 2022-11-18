package com.kuka.med.lbrmed.examples;

import javax.inject.Inject;

import com.kuka.med.devicemodel.LBRMed;
import com.kuka.med.mastering.IMasteringService;
import com.kuka.med.mastering.MedApplicationCategory;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.motionModel.PTPHome;
import com.kuka.task.ITaskLogger;

/**
 * This example shows how to master the Joint number 2 of the robot. If the Joint is already mastered, it invalidates
 * the mastering first. The application can even start if the Joint is not mastered, due to the annotation
 * "@MedApplicationCategory(checkMastering = false)".
 */
@MedApplicationCategory(checkMastering = false)
public class UnmasteredSampleApp extends RoboticsAPIApplication
{
    @Inject
    private ITaskLogger _logger;

    @Inject
    private LBRMed _lbrMed;

    @Override
    public void run()
    {
        IMasteringService masteringCap = _lbrMed.getCapability(IMasteringService.class);

        //Invalidate Joint 2 mastering if it is already mastered 
        if (masteringCap.isJointMastered(1))
        {
            _logger.info("Joint 2 is mastered ... invalidating its mastering");
            masteringCap.invalidateMastering(1);
        }

        //Master Joint 2
        _logger.info("Mastering Joint 2...");
        if (masteringCap.masterJoint(1))
        {
            _logger.info("Mastering Joint 2 finished");
        }
        else
        {
            _logger.error("Mastering Joint 2 failed");
        }

        //Move to home position (will fail if not all axes are mastered) 
        //WARNING: Make sure, that the pose is collision free!
        _logger.info("Moving home...");
        _lbrMed.getFlange().move(new PTPHome().setJointVelocityRel(0.2));

        _logger.info("Application finished");
    }
}
