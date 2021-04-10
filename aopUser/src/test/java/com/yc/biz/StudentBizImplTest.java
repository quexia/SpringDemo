package com.yc.biz;

import com.yc.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class StudentBizImplTest {
    @Autowired
    private StudentBizImpl studentBizImpl;

    @Test
    public void add() {
        studentBizImpl.add("123");
    }

    @Test
    public void update() {
        studentBizImpl.update("update");
    }

    @Test
    public void find() {
        studentBizImpl.find("find");
    }
}