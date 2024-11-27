package ru.itmo.idu.jts_geojson

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.*
import java.io.ByteArrayOutputStream
import java.io.StringBufferInputStream

class ImportExportTest {

    @Test
    fun testImportFeatureCollection() {
        val fc = GeoJSONImportExport.readFeatureCollection(javaClass.classLoader.getResourceAsStream("test_fc.geojson")!!)

        Assertions.assertNotNull(fc)
        Assertions.assertEquals(3, fc.features.size)

        Assertions.assertEquals(1.0, fc.features[0].properties["test"])
        Assertions.assertTrue(fc.features[0].geometry is Polygon)
        Assertions.assertEquals("1", fc.features[0].id)
        Assertions.assertEquals(2.0, fc.features[1].properties["test"])
        Assertions.assertTrue(fc.features[1].geometry is LineString)
        Assertions.assertEquals(2.0, fc.features[1].id)
        Assertions.assertEquals(3.0, fc.features[2].properties["test"])
        Assertions.assertTrue(fc.features[2].geometry is Point)
        Assertions.assertNull(fc.features[2].id)

        val emptyFc = GeoJSONImportExport.readFeatureCollection(javaClass.classLoader.getResourceAsStream("empty_fc.geojson")!!)
        Assertions.assertEquals(0, emptyFc.features.size)
    }

    @Test
    fun testImportSingleGeometry() {
        val geom = GeoJSONImportExport.readSingleGeometry(javaClass.classLoader.getResourceAsStream("test_fc.geojson")!!)

        Assertions.assertTrue(geom is Polygon)

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            GeoJSONImportExport.readSingleGeometry(javaClass.classLoader.getResourceAsStream("empty_fc.geojson")!!)
        }
    }

    @Test
    fun testExport() {
        val gf = GeometryFactory()
        val feature = Feature(
            gf.createPoint(Coordinate(1.0, 2.0)),
            mutableMapOf("test" to 25),
            1
        )

        val baos = ByteArrayOutputStream()
        GeoJSONImportExport.writeFeatureCollection(baos, FeatureCollection(feature))

        val str = baos.toString("UTF-8")

        val f = GeoJSONImportExport.readFeatureCollection(StringBufferInputStream(str))

        Assertions.assertEquals(1, f.features.size)
        Assertions.assertEquals(25.0, f.features[0].properties["test"])
        Assertions.assertEquals(1.0, f.features[0].id)

    }
}