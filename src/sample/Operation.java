package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.DecimalFormat;
import java.util.Locale;

public class Operation {
        SimpleIntegerProperty id;
        SimpleStringProperty debet;
        SimpleStringProperty credet;
        SimpleDoubleProperty sum;
        SimpleStringProperty data;
        SimpleStringProperty note;



    public Operation(int id,  String debet, String credet, double sum, String note, String data) {
        this.id = new SimpleIntegerProperty(id);
        this.debet = new SimpleStringProperty(debet);
        this.credet = new SimpleStringProperty(credet);
        this.sum = new SimpleDoubleProperty(sum);
        this.note = new SimpleStringProperty(note);
        this.data = new SimpleStringProperty(data);
    }

    public Operation(String debet, String credet, double sum, String note, String data) {
            this.debet = new SimpleStringProperty(debet);
            this.credet = new SimpleStringProperty(credet);
            this.sum = new SimpleDoubleProperty(sum);
            this.note = new SimpleStringProperty(note);
            this.data = new SimpleStringProperty(data);
        }

        public Operation(String debet, String credet, double sum, String data) {
            this.debet = new SimpleStringProperty(debet);
            this.credet = new SimpleStringProperty(credet);
            this.sum = new SimpleDoubleProperty(sum);
            this.note = new SimpleStringProperty("");
            this.data = new SimpleStringProperty(data);
        }

        public Operation(String debet, String credet, double sum) {
            this.debet = new SimpleStringProperty(debet);
            this.credet = new SimpleStringProperty(credet);
            this.sum = new SimpleDoubleProperty(sum);
            this.note = new SimpleStringProperty("");
            this.data = new SimpleStringProperty("");
        }



        public int getId() {
            return id.get();
        }

        public void setId(int id) {
            this.id = new SimpleIntegerProperty(id);
    }

        public String getDebet() {
            return debet.get();
        }

        public void setDebet(String debet){
            this.debet = new SimpleStringProperty(debet);
        }


        public String getCredet() {
            return credet.get();
        }

        public void setCredet(String credet) {
            this.credet = new SimpleStringProperty(credet);
        }

        public String getSum() {
             return String.format(Locale.US,"₴ %,.2f", sum.get());
        }
        public String getSum0(){
        return String.format(Locale.US, "%.2f", sum.get());
        }
        public double getSum1() {
            return sum.get();
        }
        public void setSum(double sum) {
            this.sum = new SimpleDoubleProperty(sum);
        }

        public String getNote() {
            return note.get();
        }

        public void setNote(String note) {
            this.note = new SimpleStringProperty(note);
        }

        public String getData() {
            return data.get();
        }


        public void setData(String data) {
            this.data = new SimpleStringProperty(data);
        }


    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", debet=" + debet +
                ", credet=" + credet +
                ", sum=" + sum +
                ", data=" + data +
                ", note=" + note +
                '}';
    }
}
