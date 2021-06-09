package airbnb;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Complain_table")
public class Complain {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long cmpId;
    private Long roomId;
    private Long rsvId;
    private Long payId;
    private String contents;

    @PostPersist
    public void onPostPersist(){

        /////////////////////////////////////////////////////////////////
        // complain Insert (고객이 불만을 등록함)
        /////////////////////////////////////////////////////////////////

        System.out.println("*** getPayId : " + this.getPayId());

        // Paid 된 건인지 체크
        
        boolean result = ComplainApplication.applicationContext.getBean(airbnb.external.PaymentService.class)
                        .chkPayment(this.getPayId());
        System.out.println("####### Check Result : " + result);

        if(result) {


            // 이벤트 발행 --> ComplainRegistered (불만이 등록됨)
            ComplainRegistered complainRegistered = new ComplainRegistered();
            BeanUtils.copyProperties(this, complainRegistered);
            complainRegistered.publishAfterCommit();

        }

        //airbnb.external.Payment payment = new airbnb.external.Payment();
        // mappings goes here        
        //ComplainApplication.applicationContext.getBean(airbnb.external.PaymentService.class)
            //.chkPayment(payment);

    }

    @PostUpdate
    public void onPostUpdate(){

        ///////////////////////////////////////////////////////////
        // COMPLAIN_TABLE Update 후 수행
        ///////////////////////////////////////////////////////////

        // ComplainModified 이벤트 발생시
        ComplainModified complainModified = new ComplainModified();
        BeanUtils.copyProperties(this, complainModified);
        complainModified.publishAfterCommit();


    }

    @PreRemove
    public void onPreRemove(){

        /////////////////////////////////////////////////////////
        // COMPLAIN_TABLE Delete 전 수행
        /////////////////////////////////////////////////////////

        // ComplainDeleted 이벤트 발생시
        ComplainDeleted complainDeleted = new ComplainDeleted();
        BeanUtils.copyProperties(this, complainDeleted);
        complainDeleted.publishAfterCommit();


    }


    public Long getCmpId() {
        return cmpId;
    }

    public void setCmpId(Long cmpId) {
        this.cmpId = cmpId;
    }
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    public Long getRsvId() {
        return rsvId;
    }

    public void setRsvId(Long rsvId) {
        this.rsvId = rsvId;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }




}
