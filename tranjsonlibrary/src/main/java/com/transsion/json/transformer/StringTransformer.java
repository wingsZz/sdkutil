package com.transsion.json.transformer;

public class StringTransformer extends AbstractTransformer {

    public void transform(Object object) {
        getContext().writeQuoted((String) object);
    }

}
