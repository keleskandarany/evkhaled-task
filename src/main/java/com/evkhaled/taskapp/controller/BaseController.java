package com.evkhaled.taskapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/task")
public abstract class BaseController {
    // This class holds the common behavior among Controllers. for instance, prefixing endpoints with /task
}