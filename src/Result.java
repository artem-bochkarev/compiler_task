
public class Result {
	public enum Res {
		SUCCESS, FAIL
	}
	public Tree tree;
	public Res result;
	public String errorMsg;
	public int stringNumber;
	public int position;
	
	public Result() {
		tree = null;
		result = Res.SUCCESS;
		errorMsg = "";
	}
	
	public void appendResult(Result result) {
		errorMsg += result.errorMsg;
		if ( result.result == Result.Res.FAIL ) {
			this.result = Res.FAIL;
			stringNumber = result.stringNumber;
			position = result.position;
		}else {
			tree.children.addAll(result.tree.children);
		}
	}
	
	public static Result chooseResult( Result r1, Result r2 ) {
		if ( r1.stringNumber > r2.stringNumber ) {
			return r1;
		}else {
			if ( r1.position > r2.position ) {
				return r1;
			}
		}
		return r2;
	}
	
	public void setError(String errorMsg, int position, int strNumber) {
		result = Result.Res.FAIL;
		this.errorMsg = errorMsg;
		this.position = position;
		stringNumber = strNumber;
	}
	
	public void setError(String errorMsg, int position) {
		result = Result.Res.FAIL;
		this.errorMsg = errorMsg;
		this.position = position;
		stringNumber = 0;
	}
	
	public void setError(String errorMsg) {
		result = Result.Res.FAIL;
		this.errorMsg = errorMsg;
		this.position = 0;
		stringNumber = 0;
	}
}
