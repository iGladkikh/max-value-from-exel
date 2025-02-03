package com.igladkikh.parser.service;

import java.io.IOException;

public interface ParserService {

    Long parseMaxLongWithPosition(String fileLocation, int position) throws IOException;
}
