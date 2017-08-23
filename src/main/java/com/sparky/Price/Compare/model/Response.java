package com.sparky.Price.Compare.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Response
 *
 * The Response class handles all the responses from the server and can be set on the fly
 *
 * @author chris.sheppard
 * Created by chrissheppard on 23/08/2017.
 */
@RequiredArgsConstructor
@Getter
@Setter
public class Response {
    private @NonNull HttpStatus status;
    private @NonNull String error;
    private List<?> data;
    private String redirect;
}
