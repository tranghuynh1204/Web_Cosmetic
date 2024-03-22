package hcmute.web_cosmetic.entity.order;

public enum OrderStatus {
	
	PROCESSING {
		@Override
		public String getDescription() {
			return "Order was placed by the customer";
		}
		
	},

	CONFIRMED {
		@Override
		public String getDescription() {
			return "Order was confirmed";
		}

	},
	DELIVERING {
		@Override
		public String getDescription() {
			return "Order is being delivered";
		}

	},
	DELIVERED {
		@Override
		public String getDescription() {
			return "Order was delivered";
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

