package hcmute.web_cosmetic.entity.order;

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
