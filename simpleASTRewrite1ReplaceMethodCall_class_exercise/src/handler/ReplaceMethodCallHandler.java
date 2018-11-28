/**
 */
package handler;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import analysis.JavaASTAnalyzeReplaceMethodCall;
import view.ASTRewriteViewer;
import visitor.ReplaceMethodCallVisitor;

/**
 * @since JavaSE-1.8
 */
public class ReplaceMethodCallHandler {

	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;

	@Execute
	public void execute(EPartService service) {
		// TODO Class Exercise
//      InputDialog dlgMethodToBeReplaced = new InputDialog(shell, "", "Enter method name to be replaced:", "", null);
//      if (dlgMethodToBeReplaced.open() == Window.OK) {
//         System.out.println(dlgMethodToBeReplaced.getValue());
//         
//         InputDialog dlgNewMethodInfo = new InputDialog(shell, "", "Enter new method name:", "", null);
//         if (dlgNewMethodInfo.open() == Window.OK) {
//            System.out.println(dlgNewMethodInfo.getValue());
//         }
//      }

		ReplaceMethodCallVisitor rmv = new ReplaceMethodCallVisitor();

		InputDialog dlgMethodToBeReplaced = new InputDialog(shell, null, "Enter method name to be replaced:", null,
				null);
		if(dlgMethodToBeReplaced.open() == Window.OK) {
			String curMethodName = dlgMethodToBeReplaced.getValue();

			String[] curMethodNameArray = curMethodName.split("\\.");

			rmv.CLASSNAME_STR = curMethodNameArray[0];
			rmv.METHODNAME_TRIM = curMethodNameArray[1];

			System.out.printf("%s %s\n", rmv.CLASSNAME_STR, rmv.METHODNAME_TRIM);
			InputDialog dlgMethodInfo = new InputDialog(shell, null, "Enter new method name:", curMethodName, null);
			if(dlgMethodInfo.open() == Window.OK) {
				String newMethodName = dlgMethodInfo.getValue();
				String[] newMethodNameArray = newMethodName.split("\\.");

				rmv.PKGNAME_UTILSTR = newMethodNameArray[0];
				rmv.CLASSNAME_UTILSTR = newMethodNameArray[1];
				rmv.METHODNAME_UTILSTR = newMethodNameArray[2];
			}
		}






		MPart part = service.findPart(ASTRewriteViewer.VIEWID);
		if (part != null) {
			if (part.getObject() instanceof ASTRewriteViewer) {
				JavaASTAnalyzeReplaceMethodCall analyze = new JavaASTAnalyzeReplaceMethodCall( //
						(ASTRewriteViewer) part.getObject());
				try {
					analyze.analyze();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("[DBG] Please open the part descriptor view!!");
			}
		}
	}
}