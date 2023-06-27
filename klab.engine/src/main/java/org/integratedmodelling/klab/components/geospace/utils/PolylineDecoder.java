package org.integratedmodelling.klab.components.geospace.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;

public class PolylineDecoder {

	private static final double DEFAULT_PRECISION = 1E5;

	public static Shape decode(String encoded, boolean flipCoordinates) {
		return decode(encoded, DEFAULT_PRECISION, flipCoordinates);
	}

	/**
	 * Precision should be something like 1E5 or 1E6. For OSRM routes found
	 * precision was 1E6, not the original default 1E5.
	 * 
	 * @param encoded
	 * @param precision
	 * @return
	 */
	public static Shape decode(String encoded, double precision, boolean flipCoordinates) {

		List<Coordinate> track = decodeBase64(encoded, precision, flipCoordinates);

		LineString linestring = Geospace.gFactory.createLineString(track.toArray(new Coordinate[track.size()]));

		return Shape.create(linestring, Projection.getLatLon());
	}

	/**
	 * Decodes a route with multiple stopping points, each trip leg is an element of
	 * the encoded list.
	 * 
	 * @param encoded
	 * @param precision
	 * @return
	 */
	public static Shape decode(List<String> encoded, double precision, boolean flipCoordinates) {

		List<Coordinate> track = encoded.stream().map(string -> decodeBase64(string, precision, flipCoordinates))
				.flatMap(List::stream).collect(Collectors.toList());
		LineString linestring = Geospace.gFactory.createLineString(track.toArray(new Coordinate[track.size()]));

		return Shape.create(linestring, Projection.getLatLon());
	}

	private static List<Coordinate> decodeBase64(String encoded, double precision, boolean flipCoordinates) {

		List<Coordinate> track = new ArrayList<>();
		int index = 0;
		int lat = 0, lng = 0;

		while (index < encoded.length()) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			track.add(flipCoordinates ? new Coordinate((double) lng / precision, (double) lat / precision)
					: new Coordinate((double) lat / precision, (double) lng / precision));

		}
		return track;
	}

	public static void main(String[] args) {
		System.out.println(decode("yjq~FprhvO`qDbIfuEogJ", 1E5, false));
		System.out.println(decode(
				"kckcpA{cfzAiUdGc^jKaX`HuSpF_Bj@eEjAiKl@{FDkSi@eX{ByNcAcLq@sKeAiDGgE~AmBb@}CtEmBbJp@nMrGdNtO~RzP`OjUnInZlI`SbJ|PtNbR`SfU|b@`U`f@lGf[|At_@MnNb@lX`FjW~E~M`KrV`KfXfKp_@dInWlHxW`Hnd@|Hd[zCvS~@~IbAxHnF`TtPx[jLjY|IfTxBtEaDdC_AnDO`HfG|OdDbV_@nUgCbWwHja@mF~VqD`KqBxG{JzPqExIyF~KiHhPoKrOuDpA{CdAkJ?{L[oFjBsGbHoKvM}JnF{Lj@sOQmHdCcEdFqDjGgN~ZuMxWiCfFaSva@yHhViCnR{@lNMnLS~R\\\\hGbBtZbInf@vEnPlAjEfBjGvLv`@`N|YJRjPrT`QhVvAxGc@bLyBbDaGrC}YdFoJ|DcHrCgDvHiAdMmCzOeFhJmFbByD]wDuBoHsG}QkYqIqOeO_LuMiLkUmXaP}VeUwg@kC_DqCAeB|AwFbX}Nnt@qGfWyBbCcBY]_BRsCvGeVzAcN@mjAyAm]iCaWgFsO_JySgE_QkAqISgKzAuJfHsHjDcFr@yHy@wDoBwD_DsH{A}FwAuJ{A{AkCFuCtD_Hv]uDt`@e@dC_BjBqB`@_DIo@e@gCgDM_BOcCj@uNj@{PPwFO_m@?_A|@mRfCgMbFwM~HeNjSqYzCgDzQmSnQoW|@qAfMyU\\\\{@pGcPvCgM|@yLgAuMk@oIXsJVuNuAuLqAsKkH}Oku@sw@e[_c@}CcHsBcHmEqO_FsKmKsPo[w_@aSyOsKsIsD{GkDgGcD_LuBgGgCuGsB{@mBp@iKhPePvW{LrQoF|I}DlCoDJkAaAWiHlBsHtH}NzIuVvB}Ij@iCrA_[AcP_@oKuDwP_DaM}DaLwEsDqKmFsJuCkKaBcGYgGl@cHrC}Ij@_IiDcGcDcHyKgG}G{AwAaB{AuEeAwCLyJ|EmLfG_K`G}PxByOlE{P~C_LtDeKdHoNpImAr@kGxBoGnBgGEoBaAyA}B{CuE_L{Pg@_AsF_K_BwB_GkKiBaHmA_JI_QBgFO{RAyEKmK[uDkAqQ_AoGcA_Fw@eEiA}BiBcEyD}CwKkFyHuCcBkAmNsGkE}CuDkByCeCoFsFiEoG_EuEgFaEuDcC}F}DuEmDeI}HoCgD{EkGaAaA_GeJ}EoH}ByCiB{BmByB_EcDcKcFmBu@wEoCqK{E{E_DyCiDmEsF_FmE[Y_G}DsDqAsBs@kIoBsFoBgLcEcJwE_FoCyA{@yDmDuDoCgHuCuDqAgGsCcE{AyCe@oFU{CIgC[kAc@sAqAqEmE_D_EqGuIyFsIe@s@sHeLcK{OmFeIcEuGyCwGkAkByBmDsG}IeF{GeCuCq@w@y@aAuDwDqC{EoAyCWcAc@w@_AwD_@mBOqBKoBByBLyCNcIDyFKcHBuB@qCK{N[gFKoAkBqUcBaRi@wGyBmVEq@y@uMe@qKSuGCyD@cCHaCv@iKx@eHv@cEjAwErBmGzAwDbAmChAuD|@wE^cCRwATgEAyES{HGsBCyDFsCb@kDl@_DbA}CbCqErBqB\\\\SbCuAlBg@~AUrDe@bCu@rBy@hBiAjBgBhCoDbB}BxLgPlL{MnDqDfKmJBCfJwHxAkAdFcElDeDpEaGnR}YdDoEnD{E`GcG~BsBjCy@vCa@nHEhSh@~G?rOI|C[hD]fBq@j@UvHyDbGkDlFeDzGuFlGsGPUvDsEpEoFnBoB~B}BhCqB`@YfAw@`DaBrDeB|EcC~A}@vHmEvDeC`DeCnF{DlBuAvD{BrCmBhCgAb@IzAWzI?`BBvAErFSfTsCrFu@\\\\GhAU\\\\IFZJXLVPPPNTHRDT@TCTGRMPONULWHYD]@]?]C]bAc@NInJ_HnGaGZ]jNqOjG_H`AeAjRyShLkMhG_H|FeGxH{Jz[ya@jCqDvJeQ|K_R|a@gr@rJwLbVs[|PiOTARCPKPMNSJUFYrIaI`@[hI{GbAqAhIiKbKuKxMwMx@w@vCwCxG}FbMwK|V{MVMVSRYN]Ja@Fc@zGwAbCG|DCnA@tDf@xCGvI_CfBu@~@i@ZQ|DyBtIeF|BuArJ{FpDiBlDkAbCk@~MuBrD[jB_@nBcApAaApAaBv@_BrAgDhAkCvEuKnCwGx@gBl@sARa@p@yAlAmC~AgCnBuB~AkAnC}AlCyAzEaCrLoFvGoC@gDfBm\\\\f@{KPoInRZnDCrBe@tDcBlDgD`@]",
				1E6, false));
		List<String> list = new ArrayList<String>() {
			{
				add("yjq~FprhvO`qDbIfuEogJ");
				add("kckcpA{cfzAiUdGc^jKaX`HuSpF_Bj@eEjAiKl@{FDkSi@eX{ByNcAcLq@sKeAiDGgE~AmBb@}CtEmBbJp@nMrGdNtO~RzP`OjUnInZlI`SbJ|PtNbR`SfU|b@`U`f@lGf[|At_@MnNb@lX`FjW~E~M`KrV`KfXfKp_@dInWlHxW`Hnd@|Hd[zCvS~@~IbAxHnF`TtPx[jLjY|IfTxBtEaDdC_AnDO`HfG|OdDbV_@nUgCbWwHja@mF~VqD`KqBxG{JzPqExIyF~KiHhPoKrOuDpA{CdAkJ?{L[oFjBsGbHoKvM}JnF{Lj@sOQmHdCcEdFqDjGgN~ZuMxWiCfFaSva@yHhViCnR{@lNMnLS~R\\\\hGbBtZbInf@vEnPlAjEfBjGvLv`@`N|YJRjPrT`QhVvAxGc@bLyBbDaGrC}YdFoJ|DcHrCgDvHiAdMmCzOeFhJmFbByD]wDuBoHsG}QkYqIqOeO_LuMiLkUmXaP}VeUwg@kC_DqCAeB|AwFbX}Nnt@qGfWyBbCcBY]_BRsCvGeVzAcN@mjAyAm]iCaWgFsO_JySgE_QkAqISgKzAuJfHsHjDcFr@yHy@wDoBwD_DsH{A}FwAuJ{A{AkCFuCtD_Hv]uDt`@e@dC_BjBqB`@_DIo@e@gCgDM_BOcCj@uNj@{PPwFO_m@?_A|@mRfCgMbFwM~HeNjSqYzCgDzQmSnQoW|@qAfMyU\\\\{@pGcPvCgM|@yLgAuMk@oIXsJVuNuAuLqAsKkH}Oku@sw@e[_c@}CcHsBcHmEqO_FsKmKsPo[w_@aSyOsKsIsD{GkDgGcD_LuBgGgCuGsB{@mBp@iKhPePvW{LrQoF|I}DlCoDJkAaAWiHlBsHtH}NzIuVvB}Ij@iCrA_[AcP_@oKuDwP_DaM}DaLwEsDqKmFsJuCkKaBcGYgGl@cHrC}Ij@_IiDcGcDcHyKgG}G{AwAaB{AuEeAwCLyJ|EmLfG_K`G}PxByOlE{P~C_LtDeKdHoNpImAr@kGxBoGnBgGEoBaAyA}B{CuE_L{Pg@_AsF_K_BwB_GkKiBaHmA_JI_QBgFO{RAyEKmK[uDkAqQ_AoGcA_Fw@eEiA}BiBcEyD}CwKkFyHuCcBkAmNsGkE}CuDkByCeCoFsFiEoG_EuEgFaEuDcC}F}DuEmDeI}HoCgD{EkGaAaA_GeJ}EoH}ByCiB{BmByB_EcDcKcFmBu@wEoCqK{E{E_DyCiDmEsF_FmE[Y_G}DsDqAsBs@kIoBsFoBgLcEcJwE_FoCyA{@yDmDuDoCgHuCuDqAgGsCcE{AyCe@oFU{CIgC[kAc@sAqAqEmE_D_EqGuIyFsIe@s@sHeLcK{OmFeIcEuGyCwGkAkByBmDsG}IeF{GeCuCq@w@y@aAuDwDqC{EoAyCWcAc@w@_AwD_@mBOqBKoBByBLyCNcIDyFKcHBuB@qCK{N[gFKoAkBqUcBaRi@wGyBmVEq@y@uMe@qKSuGCyD@cCHaCv@iKx@eHv@cEjAwErBmGzAwDbAmChAuD|@wE^cCRwATgEAyES{HGsBCyDFsCb@kDl@_DbA}CbCqErBqB\\\\SbCuAlBg@~AUrDe@bCu@rBy@hBiAjBgBhCoDbB}BxLgPlL{MnDqDfKmJBCfJwHxAkAdFcElDeDpEaGnR}YdDoEnD{E`GcG~BsBjCy@vCa@nHEhSh@~G?rOI|C[hD]fBq@j@UvHyDbGkDlFeDzGuFlGsGPUvDsEpEoFnBoB~B}BhCqB`@YfAw@`DaBrDeB|EcC~A}@vHmEvDeC`DeCnF{DlBuAvD{BrCmBhCgAb@IzAWzI?`BBvAErFSfTsCrFu@\\\\GhAU\\\\IFZJXLVPPPNTHRDT@TCTGRMPONULWHYD]@]?]C]bAc@NInJ_HnGaGZ]jNqOjG_H`AeAjRyShLkMhG_H|FeGxH{Jz[ya@jCqDvJeQ|K_R|a@gr@rJwLbVs[|PiOTARCPKPMNSJUFYrIaI`@[hI{GbAqAhIiKbKuKxMwMx@w@vCwCxG}FbMwK|V{MVMVSRYN]Ja@Fc@zGwAbCG|DCnA@tDf@xCGvI_CfBu@~@i@ZQ|DyBtIeF|BuArJ{FpDiBlDkAbCk@~MuBrD[jB_@nBcApAaApAaBv@_BrAgDhAkCvEuKnCwGx@gBl@sARa@p@yAlAmC~AgCnBuB~AkAnC}AlCyAzEaCrLoFvGoC@gDfBm\\\\f@{KPoInRZnDCrBe@tDcBlDgD`@]");
			}
		};
		System.out.println(decode(list, 1E6, false));
	}

}