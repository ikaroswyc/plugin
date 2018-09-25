package simpleVisitorPattern.visitor;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Brake;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;

public class MyReverseVisitor extends CarPartVisitor{

	@Override
	public void visit(Wheel part) {
		// TODO Auto-generated method stub
		part.setName(reverseHelper(part.getName()));
		part.setModelNumberWheel(reverseHelper(part.getModelNumberWheel()));
		part.setModelYearWheel(reverseHelper(part.getModelYearWheel()));
	}

	@Override
	public void visit(Engine part) {
		// TODO Auto-generated method stub
		part.setName(reverseHelper(part.getName()));
		part.setModelNumberEngine(reverseHelper(part.getModelNumberEngine()));
		part.setModelYearEngine(reverseHelper(part.getModelYearEngine()));
	}

	@Override
	public void visit(Body part) {
		// TODO Auto-generated method stub
		part.setName(reverseHelper(part.getName()));
		part.setModelNumberBody(reverseHelper(part.getModelNumberBody()));
		part.setModelYearBody(reverseHelper(part.getModelYearBody()));
	}

	@Override
	public void visit(Brake part) {
		// TODO Auto-generated method stub
		part.setName(reverseHelper(part.getName())); 
		part.setModelNumberBrake(reverseHelper(part.getModelNumberBrake()));
		part.setModelYearBrake(reverseHelper(part.getModelYearBrake()));
	}
	
	public String reverseHelper(String s){
		StringBuilder sb = new StringBuilder(s);		
		sb.reverse();
		
		String[] str = sb.toString().split("\\s+");
		String string = "";
		for(int i = 1; i < str.length + 1; i++){
			string = string + str[str.length - i] + " ";
		}
		
		return  string.trim();
	}

}
