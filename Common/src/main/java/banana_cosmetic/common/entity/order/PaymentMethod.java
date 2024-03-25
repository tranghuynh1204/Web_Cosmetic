package banana_cosmetic.common.entity.order;

public enum PaymentMethod {
     MOMO{
        @Override
        public String getDescription() {
            return "Order was paid by MOMO";
        }

    },

    COD {
        @Override
        public String getDescription() {
            return "COD";
        }
    };

    public abstract String getDescription();
}
