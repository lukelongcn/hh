package com.transfer.db.model;

import com.transfer.db.entity.BounsDetails;
import com.transfer.db.entity.Oratrans;
import lombok.Data;

/**
 * Created by itservice on 2017/12/12.
 */
@Data
public class OratransWrap {
    private Oratrans oratrans;

    private BounsDetails bounsDetails;

    public OratransWrap(Oratrans oratrans, BounsDetails bounsDetails) {
        if (!oratrans.getOratransOId().equals(bounsDetails.getOratransOId())) {
            throw new RuntimeException("id 不相等");
        }

        this.oratrans = oratrans;
        this.bounsDetails = bounsDetails;

    }


    public OratransWrap(){}
}
