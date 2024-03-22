package hcmute.web_cosmetic.entity.order;

public enum OrderStatus {
	
	NEW {
		@Override
		public String getDescription() {
			return "Order was placed by the customer";
		}
		
	}, 
	
	CANCELLED {
		@Override
		public String getDescription() {
			return "Order was rejected";
		}
	}; 
	
	public abstract String getDescription();
}

