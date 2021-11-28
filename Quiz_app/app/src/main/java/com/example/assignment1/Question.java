package com.example.assignment1;

public class Question {

    private static final String TAG = Question.class.getSimpleName();

    //XML tags the define Question properties
    public static final String XML_QUESTION = "question";
    public static final String XML_QUESTION_TEXT = "question_text";
    public static final String XML_ANSWER = "answer";
    public static final String XML_ATTR_CONTRIBUTER = "contributor";

    private String mQuestionString; //id of string resource representing the question
    private String mContributor; //author or contributor of the question
    private String mOptionA;
    private String mOptionB;
    private String mOptionC;
    private String mOptionD;
    private String mOptionE;

    //Constructor used when the question has a contributor
    public Question(String aQuestion, String optionA, String optionB, String optionC, String optionD, String optionE){
        mQuestionString = aQuestion;
        mContributor = "anonymous";
        mOptionA = optionA;
        mOptionB = optionB;
        mOptionC = optionC;
        mOptionD = optionD;
        mOptionE = optionE;
    }
    //Constructor used when the question doesn't have a contributor
    public Question(String aQuestion, String optionA, String optionB, String optionC, String optionD, String optionE,String contributer){
        mQuestionString = aQuestion;
        if(contributer != null && !contributer.isEmpty())
            mContributor = contributer;
        else
            mContributor = "anonymous";
        mOptionA = optionA;
        mOptionB = optionB;
        mOptionC = optionC;
        mOptionD = optionD;
        mOptionE = optionE;
    }


    public String getQuestionString(){return mQuestionString;}
    public String getContributer(){return mContributor;}
    //format of the question output
    public String toString(){
        String toReturn = "";
        if(mContributor != null && !mContributor.isEmpty())
            toReturn += "[" + mContributor + "] ";
        toReturn += mQuestionString;
        return toReturn;
    }

    public String getmOptionA() {
        return mOptionA;
    }

    public String getmOptionB() {
        return mOptionB;
    }

    public String getmOptionC() {
        return mOptionC;
    }

    public String getmOptionD() {
        return mOptionD;
    }

    public String getmOptionE() {
        return mOptionE;
    }

    public char getIndex(){
        return mQuestionString.charAt(0);
    }
}
