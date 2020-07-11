package pl.icwt.ecabs.consts;

public class AmqpConsts {

    public static final String MESSAGE_EXCHANGE = "ecabs.message.x";
    public static final String BOOKING_EXCHANGE = "ecabs.booking.x";
    public static final String AUDIT_QUEUE = "ecabs.messageaudit.q";
    public static final String BOOKING_ADD_QUEUE = "ecabs.bookingadd.q";
    public static final String BOOKING_EDIT_QUEUE = "ecabs.bookingedit.q";
    public static final String BOOKING_DEL_QUEUE = "ecabs.bookingdel.q";
    public static final String BOOKING_ADD_TOPIC = "booking.add";
    public static final String BOOKING_EDIT_TOPIC = "booking.edit";
    public static final String BOOKING_DEL_TOPIC = "booking.del";

    public static final String DEAD_LETTER_QUEUE = "ecabs.dlq";
    public static final String DEAD_LETTER_EXCHANGE = "ecabs.dlx";


    private AmqpConsts() {
    }


}
