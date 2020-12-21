package com.etone.project.utils.geos;
/**
 * geoserver远程操作配置
 * @author guojian
 * @version $$Revision: 14079 $$
 */
public enum Configure {
    /**
     * Only setup the first feature/coverages type available in the data
     * store/coveragestore. This is the default value.
     **/
    first,
    /**
     * Do not configure any feature types/coverages.
     */
    none,
    /**
     * cnfigure all featuretypes/coverages.
     */
    all,
}
