import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Tree {
	String label;
	String uniqueName;
	
	List<Tree> children;
	public Tree(String node, Tree ... children) {
		this.label = node;
		this.children = Arrays.asList(children);
		this.uniqueName =  Integer.toString(this.hashCode());
	}
	public Tree(String node) {
		this.label = node;
		this.uniqueName =  Integer.toString(this.hashCode());
		children = new ArrayList<Tree>();
	}
	
	private String toString(String prefix) {
		String s = "";
		s += label;
		if (children != null) {
			s += " -> ";
			for (Tree tree:children) {
				s += tree.label + " ";
			}
		}
		s += "\n";
		if (children != null) {
			for (Tree tree:children) {
				s += prefix + "\t";
				s += tree.toString(prefix + "\t");
			}
		}
		return s;
	}

	public String toString() {
		String s = "";
		s += label;
		if (children != null) {
			s += " -> ";
			for (Tree tree:children) {
				s += tree.label + " ";
			}
		}
		s += "\n";
		if (children != null) {
			for (Tree tree:children) {
				s += "\t";
				s += tree.toString("\t");
			}
		}
		return s;
	}
	public String toDot() {
		String s = this.uniqueName + " [label=\"" + this.label + "\"];\n";
		if (children != null) {
			for (Tree tree:children) {
				s += tree.toDot();
				s += this.uniqueName + " -> " + tree.uniqueName + ";\n";
			}
		}
		return s;
	}
	
	//auto-generated
	public int value;
}
