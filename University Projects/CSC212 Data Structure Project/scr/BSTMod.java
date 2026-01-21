public class BSTMod<T> {
	    private BSTNodeMod<T> root, current;

	    public BSTMod() {
	        current = root = null;
	    }

	    public boolean empty() {
	        return root == null;
	    }

	    public boolean full() {
	        return false;
	    }

	    public T retrieve() {
	        return current.data;
	    }

	    public boolean findKey(int k) {
	        BSTNodeMod<T> p = root;
	        while (p != null) {
	            current = p;
	            if (k == p.key) {
	                return true;
	            } else if (k > p.key) {
	                p = p.right;
	            } else {
	                p = p.left;
	            }
	        }
	        return false;
	    }

	    public boolean insert(int k, T val) {
	        if (root == null) {
	            current = root = new BSTNodeMod<>(k, val);
	            return true;
	        }
	        BSTNodeMod<T> p = root;
	        while (p != null) {
	            current = p;
	            if (k < p.key) {
	                p = p.left;
	            } else {
	                p = p.right;
	            }
	        }
	        
	        BSTNodeMod<T> tmp = new BSTNodeMod<>(k, val);
	        if (k < current.key) {
	            current.left = tmp;
	        } else {
	            current.right = tmp;
	        }
	        current = tmp;
	        return true;
	    }

	    public void display_decreasing() {
	        if (root == null) {
	            System.out.println("empty tree");
	        } else {
	            System.out.println("DocID Score");
	            decreasing(root);
	        }
	    }

	    private void decreasing(BSTNodeMod<T> p) {
	        if (p == null) return;
	        decreasing(p.right);
	        System.out.print(p.data);
	        System.out.println(" " + p.key);
	        decreasing(p.left);
	    }
	}
