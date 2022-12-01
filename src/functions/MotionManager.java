package functions;

import com.kuka.motion.IMotionContainer;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MotionManager {
  private IMotionContainer m_motionContainer = null;
  @Inject public MotionManager() {}
  
  public void setMotionContainer(IMotionContainer mc) {
    m_motionContainer = mc;
  }
  
  public IMotionContainer getMotionContainer() {
    return m_motionContainer;
  }
}
