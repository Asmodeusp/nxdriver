package com.saimawzc.freight.dto.sendcar;

import java.util.List;

/*****
 * 运输小单
 * */
public class TrantSamllOrderDto {
    private String id;
    private String origin;
    private String destination;

    private String path[];

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }
}
