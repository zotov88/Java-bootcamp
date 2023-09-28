package edu.school21.spring.preprocessor;

public class PreProcessorToUpperImpl implements PreProcessor {

    @Override
    public String processMsg(String msg) {
        return msg.toUpperCase();
    }
}
