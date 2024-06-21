package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;


public class MarkedStatus {
    private int grade;
    private boolean slabbed;
//    private Set<String> signatures;

    private Map<String, Boolean> signatures; // Tracks if a signature has been CALCULATED into values
    private int authenticatedCount;
    private int authCalcCount; // How many auth sigs have been calculated
    private double baseValue;
    private double fullValue;
    private boolean status;
    private static final String NO_SIGNATURE = "";
    private static final int NO_GRADE = 0;

    public MarkedStatus(double value){
        this.status = false;
        this.grade = NO_GRADE;
        this.authenticatedCount = 0;
        this.authCalcCount = 0;
        this.signatures = new HashMap<>();
        this.slabbed = false;
        this.baseValue = value;
        this.fullValue = value;
    }

    public void setBaseValue(double value){
        this.baseValue = value;
    }
    public void setGrade(int grade) {
        if (this.baseValue == 0){
            baseValue = .1;
        }
        if (grade == 1) {
            this.baseValue *= .10;
        } else if (grade >= 2 && grade <= 10){
            this.baseValue *= Math.log10(grade);
        } else {
            return; // Throw an error instead??
        }
        this.grade = grade;
        this.status = true;
        this.fullValue = baseValue;
    }

    public void slabComic() {
        if (grade != NO_GRADE){
            this.slabbed = true;
        }
    }

    public void setSignature(String signature) {
        if (grade == NO_GRADE) {
            setGrade(1);
        }
        if (!this.signatures.containsKey(signature) && !signature.equals(NO_SIGNATURE)) {
            this.signatures.put(signature, false);
        }
    }

    public void authenticateSignatures(int amount) {
        if (amount + this.authenticatedCount > this.signatures.size()){
            amount = this.signatures.size() - authenticatedCount;
        }
        if (!this.signatures.isEmpty() && grade != 0){
            this.authenticatedCount += amount;
            this.authCalcCount += amount;
        }
    }

    public void calculateValue(){
        if(slabbed) {
            this.fullValue = 2*baseValue;
        }
        for(String signature : this.signatures.keySet()){
            if (!this.signatures.get(signature)){
                this.fullValue += baseValue*.05;
                this.signatures.put(signature, true);
            }
        }
        int authRemoveCount = 0;
        for (int i = 0; i < authCalcCount; i++){
            this.fullValue += baseValue*.20;
            authRemoveCount++;
        }

        this.authCalcCount -= authRemoveCount;

    }

    public boolean getStatus() {
        return status;
    }

    public int getGrade() {
        return grade;
    }

    public BigDecimal getValue(){
        calculateValue();

        return new BigDecimal(this.fullValue).setScale(2, RoundingMode.HALF_UP);
    }

}
