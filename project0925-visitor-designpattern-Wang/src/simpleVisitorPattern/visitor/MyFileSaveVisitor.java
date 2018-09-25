package simpleVisitorPattern.visitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import util.UtilFile;
import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Brake;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;

public class MyFileSaveVisitor extends CarPartVisitor{
	List<String> listContents = new ArrayList<>();
	String workDir = System.getProperty("user.dir");
	String filePath = workDir + File.separator + "output.csv";
	
	@Override
	public void visit(Wheel part) {
		// TODO Auto-generated method stub]
		listContents.add(part.getName() + "," + part.getModelNumberWheel() + "," + part.getModelYearWheel());
		try {
			UtilFile.saveFile(filePath, listContents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void visit(Engine part) {
		// TODO Auto-generated method stub
		listContents.add(part.getName()+ "," + part.getModelNumberEngine()+ "," + part.getModelYearEngine());
		try {
			UtilFile.saveFile(filePath, listContents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void visit(Body part) {
		// TODO Auto-generated method stub
		listContents.add(part.getName() + "," + part.getModelNumberBody() + "," + part.getModelYearBody());
		try {
			UtilFile.saveFile(filePath, listContents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void visit(Brake part) {
		// TODO Auto-generated method stub
		listContents.add(part.getName() + "," + part.getModelNumberBrake() + "," + part.getModelYearBrake());
		try {
			UtilFile.saveFile(filePath, listContents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
