package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.service.PagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagesServiceImpl implements PagesService {

    @Autowired
    private UserPersonalInfoRepository userPersonalInfoRepository;

    @Override
    public boolean isVerifiedProfileAccessKey(String profileAccessKey) {
        return userPersonalInfoRepository.existsByProfileAccessKey(profileAccessKey);
    }
}
