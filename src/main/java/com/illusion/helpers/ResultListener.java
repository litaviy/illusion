package com.illusion.helpers;

/**
 * Created by rammstein on 27.07.16.
 */
public interface ResultListener {

    void onSuccessResult();

    void onFailureResult(String error);
}
