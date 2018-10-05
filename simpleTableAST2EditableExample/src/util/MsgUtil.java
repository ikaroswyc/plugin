package util;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class MsgUtil {
   @Inject
   @Named(IServiceConstants.ACTIVE_SHELL)
   static Shell shell;

   public static void openWarning() {
      MessageDialog.openWarning(shell, "Warning", "I am warning you!");
   }

   public static boolean openQuestion() {
      return MessageDialog.openQuestion(shell, "Question", "Really, really?");
   }
   
   public static void openWarning(String str) {
	      MessageDialog.openWarning(shell, "Question", str);
   }
   public static boolean openQuestion(String str) {
	      return MessageDialog.openQuestion(shell, "Question", str);
   }


}
