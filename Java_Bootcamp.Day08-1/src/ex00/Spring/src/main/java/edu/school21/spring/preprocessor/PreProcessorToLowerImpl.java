package edu.school21.spring.preprocessor;

public class PreProcessorToLowerImpl implements PreProcessor {

    @Override
    public String processMsg(String msg) {
        return msg.toLowerCase();
    }
}
