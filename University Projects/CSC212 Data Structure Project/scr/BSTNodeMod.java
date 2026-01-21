
	class BSTNodeMod<T> {
	    public int key;
	    public T data;
	    public BSTNodeMod<T> left, right;

	    public BSTNodeMod(int key, T data) {
	        this.key = key;
	        this.data = data;
	        left = right = null;
	    }
	}

	