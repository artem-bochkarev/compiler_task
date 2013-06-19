
public class Result {
	public enum Res {
		SUCCESS, FAIL
	}
	public Tree tree;
	public Res result;
	public String errorMsg;
	public int stringNumber;
	public int position;
	public boolean skipped;
	public String myName;
	
	public Result() throws Exception {
		throw new Exception("NoNoNO!!");
	}
	
	public Result(String name) {
		result = Res.SUCCESS;
		errorMsg = "";
		skipped = false;
		tree = new Tree(name);
		myName = name;
	}
	
	public void appendResult(Result result) {
		errorMsg += result.errorMsg;
		if ( result.result == Result.Res.FAIL ) {
			this.result = Res.FAIL;
			stringNumber = result.stringNumber;
			position = result.position;
		}else {
			tree.children.add(result.tree);
		}
	}
	
	public void addChildren(String name) {
		tree.children.add(new Tree(name));
	}
	
	public static Result chooseResult( Result r1, Result r2 ) {
		if ( r1.stringNumber == r2.stringNumber ) {
			if ( r1.position > r2.position ) {
				return r1;
			}else {
				return r2;
			}
		}
		if ( r1.stringNumber > r2.stringNumber ) {
			return r1;
		}
		else {
			return r2;
		}
	}
	
	public void setError(String errorMsg, int position, int strNumber, boolean isSkipped) {
		result = Result.Res.FAIL;
		this.errorMsg = strNumber + ":" + position + ":" + "Error in " + myName + " - " + errorMsg;
		this.position = position;
		stringNumber = strNumber;
		skipped = isSkipped;
	}
	
	public void setError(String errorMsg, int position, int strNumber) {
		result = Result.Res.FAIL;
		this.errorMsg = strNumber + ":" + position + ":" + "Error in " + myName + " - " + errorMsg;
		this.position = position;
		stringNumber = strNumber;
	}
	
	public void setError(String errorMsg, int position) {
		result = Result.Res.FAIL;
		this.errorMsg = position + ":" + "Error in " + myName + " - " + errorMsg;
		this.position = position;
		stringNumber = 0;
	}
	
	public void setError(String errorMsg) {
		result = Result.Res.FAIL;
		this.errorMsg = "Error in " + myName + " - " + errorMsg;
		this.position = 0;
		stringNumber = 0;
	}
}
