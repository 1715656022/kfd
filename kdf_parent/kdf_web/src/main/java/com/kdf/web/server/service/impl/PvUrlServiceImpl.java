package com.kdf.web.server.service.impl;

import com.kdf.web.server.repository.PvUrlRepository;
import com.kdf.web.server.service.PvUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PvUrlServiceImpl implements PvUrlService {

    @Autowired
    private PvUrlRepository pvUrlRepository;


}
