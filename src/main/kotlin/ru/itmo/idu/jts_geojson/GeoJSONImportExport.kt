@file:Suppress("MemberVisibilityCanBePrivate")

package ru.itmo.idu.jts_geojson

import com.github.filosganga.geogson.gson.GeometryAdapterFactory
import com.github.filosganga.geogson.jts.JtsAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.locationtech.jts.geom.Geometry
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * Contains common routines for reading and writing GeoJSON files
 */
@Suppress("unused")
object GeoJSONImportExport {

    /**
     * This GSON instance is set up correctly for reading and writing GeoJSON objects.
     * It transforms GeoJSON geometry representation into JTS Geometry classes.
     */
    val gson: Gson = GsonBuilder()
        .registerTypeAdapterFactory(GeometryAdapterFactory())
        .registerTypeAdapterFactory(JtsAdapterFactory())
        .serializeSpecialFloatingPointValues()
        .create()

    /**
     * Reads GeoJSON file as FeatureCollection object
     */
    fun readFeatureCollection(stream: InputStream): FeatureCollection {
        return gson.fromJson(InputStreamReader(stream), FeatureCollection::class.java)
            ?: throw IllegalArgumentException("Provided stream does not contain valid GeoJSON")
    }

    /**
     * Reads single geometry from a GeoJSON file (geometry of a first Feature in that file if there are multiple Features)
     */
    fun readSingleGeometry(stream: InputStream): Geometry {
        val featureCollection = readFeatureCollection(stream)
        if (featureCollection.features.isEmpty()) {
            throw IllegalArgumentException("Empty feature collection")
        }
        return featureCollection.features[0].geometry
    }

    /**
     * Reads a list of geometries from given GeoJSON file, discarding all properties.
     */
    fun readGeometries(stream: InputStream): List<Geometry> {
        return readFeatureCollection(stream).features.map { it.geometry }
    }

    fun writeFeatureCollection(outputStream: OutputStream, fc: FeatureCollection) {
        OutputStreamWriter(outputStream).use {
            gson.toJson(fc, it)
        }
    }

    fun writeFeatures(outputStream: OutputStream, features: List<Feature>) {
        writeFeatureCollection(outputStream, FeatureCollection(features))
    }

    fun writeFeatures(outputStream: OutputStream, vararg features: Feature) {
        writeFeatureCollection(outputStream, FeatureCollection(features.toList()))
    }

    fun writeGeometries(outputStream: OutputStream, geometries: List<Geometry>) {
        val fc = FeatureCollection(
            geometries.map { Feature(it) }
        )
        writeFeatureCollection(outputStream, fc)
    }

    fun writeGeometries(outputStream: OutputStream, vararg geometries: Geometry) {
        writeGeometries(outputStream, geometries.toList())
    }
}