# Input #
```
b# OPEN_CONNECTION_REQ> 	                ;        ;      
b# OPEN_CONNECTION_RES<                         ;        ;   b# CONNECTION_STARTED_EVT>
                                                ;<f#DATA>; 
                                                ;        ;
d# CLOSE_CONNECTION_REQ>                        ;        ;   
d# CLOSE_CONNECTION_RES(OK)<                    ;        ;   
                                                ;        ;
                                                ;        ;
c# CONNECTION_CLOSED_EVT<                       ;        ;   c# CONNECTION_CLOSED_EVT>
```


# Output #

![http://msc2svg.googlepages.com/output-screenshot.png](http://msc2svg.googlepages.com/output-screenshot.png)