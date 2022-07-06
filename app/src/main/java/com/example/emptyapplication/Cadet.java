package com.example.emptyapplication;

public class Cadet {
    private String secondName;
    private int dutyCount;
    private int worksCount;
    private int cleanCount;
    private int realesCount;

    public Cadet(String secondName, int dutyCount, int worksCount, int cleanCount, int realesCount) {
        this.secondName = secondName;
        this.dutyCount = dutyCount;
        this.worksCount = worksCount;
        this.cleanCount = cleanCount;
        this.realesCount = realesCount;
    }

    public Cadet() {
    }

    public int getRealesCount() {
        return realesCount;
    }

    public void setRealesCount(int realesCount) {
        this.realesCount = realesCount;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getDutyCount() {
        return dutyCount;
    }

    public void setDutyCount(int dutyCount) {
        this.dutyCount = dutyCount;
    }

    public int getWorksCount() {
        return worksCount;
    }

    public void setWorksCount(int worksCount) {
        this.worksCount = worksCount;
    }

    public int getCleanCount() {
        return cleanCount;
    }

    public void setCleanCount(int cleanCount) {
        this.cleanCount = cleanCount;
    }

    public String getFullName(){
        return String.format("%s", secondName);
    }

    public int getValueOfNeedType(TypeOfWork typeOfWork){

        if(typeOfWork.equals(TypeOfWork.Duty))
            return dutyCount;

        else if(typeOfWork.equals(TypeOfWork.Work))
            return worksCount;

        else if(typeOfWork.equals(TypeOfWork.Clean))
            return cleanCount;

        else
            return realesCount;
    }

    public void setValueOfNeedType(TypeOfWork typeOfWork, int value){

        if(typeOfWork.equals(TypeOfWork.Duty))
            dutyCount = value;

        else if(typeOfWork.equals(TypeOfWork.Work))
            worksCount = value;

        else if(typeOfWork.equals(TypeOfWork.Clean))
            cleanCount = value;

        else
            realesCount = value;

    }
}
