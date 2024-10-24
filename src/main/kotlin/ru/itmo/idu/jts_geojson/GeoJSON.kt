@file:Suppress("MemberVisibilityCanBePrivate")

package ru.itmo.idu.jts_geojson

import org.locationtech.jts.geom.Geometry

/**
 * Base class for all GeoJSON models, contains only type field
 */
open class BaseGeoJSONObject(
    val type: String
)

/**
 * Feature object, contains geometry and a map of tags
 */
class Feature(
    val geometry: Geometry,
    val properties: MutableMap<String, Any>
) : BaseGeoJSONObject("Feature") {

    constructor(geometry: Geometry): this(geometry, mutableMapOf())

    override fun toString(): String {
        // we do not want to print all geometry contents as they are very large
        return "Feature{" +
                "type='" + type + '\'' +
                ", properties=" + properties +
                '}'
    }
}

/**
 * GeoJSON FeatureCollection, which is a list of features.
 * Usually this is a root element in .geojson files
 */
class FeatureCollection(
    val features: List<Feature>
) : BaseGeoJSONObject("FeatureCollection") {

    constructor(): this(listOf())

    constructor(singleFeature: Feature) : this(listOf(singleFeature)) {}

}