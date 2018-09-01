import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import Util.UtilFile;
public class ReadFileShowHelloHandler {
	
	String filePath = "C:\\Users\\Yuchen\\workspace-neon3\\project0828-message-Wang\\config.txt";
	List<String> content = new ArrayList<String>(); 

	@Execute
	public void execute(Shell shell) {
		
		content = UtilFile.readFile(filePath); 
		String str = content.get(content.size()-1).substring(5);
		System.out.println(str);
		MessageDialog.openInformation(shell, "Title", "Hello" +" " + str);
	}
	
}
