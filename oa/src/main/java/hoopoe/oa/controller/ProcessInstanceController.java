package hoopoe.oa.controller;

import hoopoe.oa.service.FlowableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/processinst")
public class ProcessInstanceController {

    @Autowired
    private FlowableService flowableService;
}
