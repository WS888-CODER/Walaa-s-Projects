

	class OurBSTNode<T> {
		public String key;
		public T data;
		public OurBSTNode<T> left, right;
		
		public OurBSTNode (String key, T data) {
		this.key = key;
		this.data = data;
		left = right = null;}
	}
	