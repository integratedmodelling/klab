/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.hydrology.weather.data;

import java.util.Date;

import org.integratedmodelling.klab.exceptions.KlabException;


/**
 * Ported from C++ weathergen package on http://weathergen.sourceforge.net
 * 
 * See: Birt et al (2010): A simple stochastic weather generator for ecological modeling. 
 * 	    Env. Mod. Software 25: 1252-1255
 
 * @author Ferd
 *
 */
public class Random {

    static final long M1 = 4294967087L; // 2^32 - 209
    static final long M2 = 4294944443L; // 2^32 - 22853
    static final int MAXSTREAMS = 100;
    static final double EPSILON = 0.000000287; // equivalent to 0.0 (5 stan.dev. from mean)

    // The following constants are to numerically integrate and obtain the inverse normal
    static final double P0 = -0.322232431088;
    static final double P1 = -1.0;
    static final double P2 = -0.342242088547;
    static final double P3 = -0.0204231210245;
    static final double P4 = -0.0000453642210148;
    static final double Q0 = -0.099348462606;
    static final double Q1 = -0.588581570495;
    static final double Q2 = -0.531103462366;
    static final double Q3 = -0.10353775285;
    static final double Q4 = -0.0038560700634;
    static final double EPS1 = 0.5 - EPSILON;
    static final double EPS2 = 0.5 + EPSILON;
    static final double EPS3 = 1.0 - EPSILON;

    static long rndSeed[][] = {
            { 0L, 0L, 1L, 0L, 0L, 1L },
            { 1772212344L, 1374954571L, 2377447708L, 540628578L, 1843308759L, 549575061L },
            { 2602294560L, 1764491502L, 3872775590L, 4089362440L, 2683806282L, 437563332L },
            { 376810349L, 1545165407L, 3443838735L, 3650079346L, 1898051052L, 2606578666L },
            { 1847817841L, 3038743716L, 2014183350L, 2883836363L, 3242147124L, 1955620878L },
            { 1075987441L, 3468627582L, 2694529948L, 368150488L, 2026479331L, 2067041056L },
            { 134547324L, 4246812979L, 1700384422L, 2358888058L, 83616724L, 3045736624L },
            { 2816844169L, 885735878L, 1824365395L, 2629582008L, 3405363962L, 1835381773L },
            { 675808621L, 434584068L, 4021752986L, 3831444678L, 4193349505L, 2833414845L },
            { 2876117643L, 1466108979L, 163986545L, 1530526354L, 68578399L, 1111539974L },
            { 411040508L, 544377427L, 2887694751L, 702892456L, 758163486L, 2462939166L },
            { 3631741414L, 3388407961L, 1205439229L, 581001230L, 3728119407L, 94602786L },
            { 4267066799L, 3221182590L, 2432930550L, 813784585L, 1980232156L, 2376040999L },
            { 1601564418L, 2988901653L, 4114588926L, 2447029331L, 4071707675L, 3696447685L },
            { 3878417653L, 2549122180L, 1351098226L, 3888036970L, 1344540382L, 2430069028L },
            { 197118588L, 1885407936L, 576504243L, 439732583L, 103559440L, 3361573194L },
            { 4024454184L, 2530169746L, 2135879297L, 2516366026L, 260078159L, 2905856966L },
            { 2331743881L, 2059737664L, 186644977L, 401315249L, 72328980L, 1082588425L },
            { 694808921L, 2851138195L, 1756125381L, 1738505503L, 2662188364L, 3598740668L },
            { 2834735415L, 2017577369L, 3257393066L, 3823680297L, 2315410613L, 637316697L },
            { 4132025555L, 3700940887L, 838767760L, 2818574268L, 1375004287L, 2172829019L },
            { 3489426225L, 4112746453L, 2594929844L, 1633976546L, 1583084490L, 4147801476L },
            { 226509369L, 146935912L, 3937833922L, 1061829953L, 813052180L, 3597307501L },
            { 3256044974L, 3681783929L, 575085515L, 1457436746L, 1015379089L, 237144042L },
            { 3893937255L, 2700371479L, 2540977187L, 448868868L, 2639602201L, 3320881846L },
            { 2151265850L, 2125810247L, 1191988643L, 1600692767L, 135550530L, 395838860L },
            { 3135267209L, 3623600367L, 2626505177L, 4281737957L, 2062913934L, 2592194162L },
            { 390422829L, 1811400753L, 322793407L, 3490624807L, 3235017317L, 371104964L },
            { 2656265790L, 3996442108L, 3386349306L, 1626832241L, 1574763932L, 3706202892L },
            { 3589952455L, 1163511069L, 2697607059L, 534184903L, 3766759256L, 251252346L },
            { 2882420196L, 3003606956L, 3629906612L, 2537203610L, 2746594184L, 1661091636L },
            { 1183183944L, 583879472L, 4255459700L, 713754974L, 2922254235L, 1161913813L },
            { 4132807367L, 3628950852L, 399250763L, 3845478137L, 1309889890L, 213170736L },
            { 3112893239L, 1632688723L, 1523209273L, 1177690048L, 3460621046L, 2768883982L },
            { 2661550465L, 1554695591L, 4025759551L, 3938508444L, 4250175274L, 1555765950L },
            { 2684621241L, 1586910242L, 3611689202L, 206694063L, 6969092L, 1062099384L },
            { 235778005L, 1845387954L, 3114888710L, 2203759286L, 3114065342L, 2496367282L },
            { 2441823102L, 2650658691L, 1337568664L, 1857164665L, 1915815707L, 884425315L },
            { 4101909040L, 4166390745L, 2860172669L, 3497960192L, 3577628224L, 4213141252L },
            { 3699546758L, 3377270633L, 68718870L, 2774746460L, 1020495218L, 1575238977L },
            { 1139564619L, 2523670566L, 3355096665L, 495198678L, 2395315759L, 849476900L },
            { 635963092L, 311652233L, 262540809L, 3184560243L, 2651025088L, 789619078L },
            { 1271360621L, 4223486533L, 1390580044L, 2888955378L, 3184629747L, 630626237L },
            { 1678221843L, 231113094L, 4130363024L, 3852779467L, 1604842289L, 4212522078L },
            { 2778593270L, 1004903484L, 2973098739L, 665751699L, 2332674775L, 295462521L },
            { 4198005471L, 972878412L, 1558189575L, 55781237L, 3893360589L, 1732945031L },
            { 1152796221L, 1778239045L, 1459562806L, 3905398533L, 3769475924L, 2644342737L },
            { 2510032067L, 3734379515L, 531296007L, 701842463L, 2074273193L, 2292680950L },
            { 2971989859L, 2256121557L, 2142214447L, 3137353222L, 773588230L, 3902637774L },
            { 4229774628L, 121176046L, 3728703289L, 3849382323L, 2003165682L, 1467065642L },
            { 323392186L, 3824746296L, 1629368039L, 739902158L, 1994020456L, 1037183254L },
            { 513283862L, 2188379510L, 1957928689L, 813409383L, 3806082123L, 635316674L },
            { 552826185L, 2555996419L, 2271338972L, 3547921306L, 1228993928L, 2361559081L },
            { 1295138976L, 1495710207L, 723074677L, 3820787179L, 3845682156L, 4174822106L },
            { 2521565200L, 31792620L, 3784456031L, 1065580104L, 3937944782L, 4215959677L },
            { 3400057703L, 2744571285L, 1393759441L, 1601776871L, 3165609536L, 835804949L },
            { 4088663797L, 662403715L, 1972116992L, 2505435866L, 3440128606L, 777899473L },
            { 3780961670L, 2157269301L, 1083414147L, 2821221342L, 3067184313L, 2993244053L },
            { 2795653207L, 2163909033L, 3809789650L, 2585746232L, 3313980374L, 3096075721L },
            { 2378158017L, 3726102874L, 2689185822L, 2369776558L, 432649940L, 1111256897L },
            { 3456908798L, 495588917L, 1480249157L, 2815947509L, 1071333363L, 1928430914L },
            { 717921372L, 2683845918L, 930457772L, 2530097953L, 2417205907L, 3022951420L },
            { 1689355612L, 1039193903L, 3613681802L, 1080205242L, 2013101883L, 2468502905L },
            { 2504068936L, 3230484718L, 1748896401L, 1453957344L, 848547757L, 161200170L },
            { 4201049117L, 444124251L, 2581583939L, 1322446501L, 681505596L, 1663004988L },
            { 2930586493L, 3370079320L, 198796253L, 3012797217L, 3840048005L, 184999535L },
            { 2532724791L, 1241080524L, 3800041958L, 3303947134L, 3174626846L, 3073510302L },
            { 14809834L, 3618645306L, 1942542077L, 669052232L, 651470251L, 1391560038L },
            { 995707287L, 1759822820L, 2501644613L, 3721319358L, 3968667126L, 2535846961L },
            { 979173856L, 1997457820L, 1968292063L, 1380431809L, 3055802991L, 540373583L },
            { 3681007982L, 2977347732L, 571110841L, 2217121181L, 2733362413L, 32186934L },
            { 1975286634L, 958506122L, 46451179L, 3475987512L, 2626451279L, 3517401221L },
            { 330144009L, 1060219535L, 1447223839L, 3165226575L, 599904263L, 3911112835L },
            { 2537161090L, 3500106754L, 926795530L, 728440468L, 1129037665L, 2789187437L },
            { 3793615118L, 2750706029L, 2156058298L, 3079033430L, 2780569996L, 3936920391L },
            { 1198120767L, 2694820585L, 2834525274L, 188651758L, 308670432L, 1369385984L },
            { 2702470127L, 19874369L, 4012779639L, 3121258228L, 1839121226L, 3861314075L },
            { 2554275712L, 719378996L, 2400430690L, 1141204239L, 1627924328L, 1297183842L },
            { 705258195L, 4161016439L, 1462878351L, 3976713745L, 1141257086L, 3425581727L },
            { 3358325932L, 2544614576L, 1863746614L, 1124881023L, 3147032679L, 3087920200L },
            { 2977113134L, 3977111791L, 4253114310L, 142443885L, 243603301L, 3716506593L },
            { 3431919302L, 1245400869L, 1324788929L, 2860157607L, 941144108L, 3857335131L },
            { 3906194044L, 1681219081L, 2843586309L, 4089310580L, 2176521638L, 690520146L },
            { 3745569546L, 792495396L, 1806539660L, 4272439405L, 1894505679L, 1392532334L },
            { 865747759L, 3079408284L, 1984574005L, 613765073L, 1551526411L, 4256443592L },
            { 2814963340L, 2834960950L, 2968542314L, 309822219L, 1823774832L, 1839552557L },
            { 2548640113L, 2862571993L, 4042326055L, 2483508296L, 2762715198L, 593338793L },
            { 2568040427L, 2120166173L, 942509028L, 2137567779L, 2626314860L, 2259019634L },
            { 1311709266L, 3938696051L, 1456683566L, 3795230520L, 1388639258L, 2800468070L },
            { 3117451406L, 928974817L, 2781895567L, 321394107L, 110152236L, 2088746743L },
            { 1875039110L, 3442179808L, 3890426888L, 1777391452L, 1835432708L, 562722092L },
            { 1448528649L, 2330442989L, 2407439339L, 3707873573L, 4196869323L, 2107506543L },
            { 2780921782L, 358207338L, 3529199325L, 4247445586L, 2383769411L, 3241037506L },
            { 1378002714L, 2723574102L, 1749215013L, 2907202697L, 2962834356L, 1188034898L },
            { 120313119L, 1494806970L, 2910978351L, 722434700L, 1419161042L, 2517182236L },
            { 1195864685L, 3838840335L, 1111591396L, 3060365866L, 2463752639L, 3666213547L },
            { 1333023911L, 1979613259L, 2132866392L, 1394964690L, 1264353313L, 3231502632L },
            { 3768306921L, 1680121724L, 805680272L, 1951253770L, 1486168061L, 2955477946L },
            { 109224445L, 2861250694L, 4196750427L, 939526471L, 1752437524L, 1145799039L },
            { 408306775L, 2148819781L, 3316724552L, 137163319L, 4180687869L, 574961213L },
            { 2386386992L, 3078479797L, 167075008L, 1253465513L, 4072841169L, 2167533564L },
            { 2315827434L, 3392038067L, 2279258261L, 1432017357L, 568998206L, 1206469405L },
            { 1678601670L, 1150247641L, 2499326926L, 699715048L, 1501364333L, 1669290797L },
            { 3957493358L, 1670076821L, 804110344L, 2866685751L, 836848697L, 3107121052L },
            { 19764713L, 3522310913L, 46276655L, 1383766567L, 2804181892L, 581103203L },
            { 2167472521L, 2014671778L, 682905578L, 958597651L, 1861637882L, 2944058806L } };

    static int SetSeed(int initValue) {
        //   ' in case the seed should be saved
        //     ' initValue is returned as a value between 0 and MAXSTREAMS
        long hold = initValue;
        if (hold <= 0)
            hold = new Date().getTime();
        initValue = (int) (hold % MAXSTREAMS);
        if (initValue == 0)
            initValue = MAXSTREAMS - 1;
        return initValue;
    }

    public static class RandomNumber {

        double getRV() {
            return oldValue;
        }

        double getMean() {
            return mean;
        }

        double getStdDev() {
            return stdDev;
        }

        int stream;

        String name;
        double mean;
        double stdDev;
        double oldNumber;
        double oldValue; // value of most recently generated random variate

        double generateRN() {

            // The multiple recursive generator of L'Ecuyer as given in Law and Kelton's 
            // Third Edition, Section 7.3.2
            long p, y;
            long s10 = rndSeed[stream][0];
            long s11 = rndSeed[stream][1];
            long s12 = rndSeed[stream][2];
            long s20 = rndSeed[stream][3];
            long s21 = rndSeed[stream][4];
            long s22 = rndSeed[stream][5];

            p = 1403580 * s11 - 810728 * s10;
            p = p % M1;
            if (p < 0)
                p += M1;
            s10 = s11;
            s11 = s12;
            s12 = p;

            p = 527612 * s22 - 1370589 * s20;
            p = p % M2;
            if (p < 0)
                p += M2;

            s20 = s21;
            s21 = s22;
            s22 = p;

            rndSeed[stream][0] = s10;
            rndSeed[stream][1] = s11;
            rndSeed[stream][2] = s12;
            rndSeed[stream][3] = s20;
            rndSeed[stream][4] = s21;
            rndSeed[stream][5] = s22;

            y = (s12 - s22) % M1;
            if (y < 0)
                y += M1;
            else if (y == 0)
                y = 4294967086L;

            oldNumber = (double) y / (double) M1;
            return oldNumber;

        }

        RandomNumber(int strm) {
            stream = SetSeed(strm);
            stream = strm;
            name = "RandomNumber";
        }
    };

    public static class Uniform extends RandomNumber {
        double low;
        double high;
        double delta;

        Uniform(int strm) {
            super(strm);
            name = "Uniform";
            low = 0.0;
            high = 1.0;
            delta = 1.0;
            mean = 0.5;
            stdDev = 0.288675;
        }

        Uniform(double low, double high, int strm) {
            super(strm);
            name = "Uniform";
            this.low = low;
            this.high = high;
            delta = high - low;
            mean = 0.5 * (low + high);
            stdDev = Math.sqrt(delta * delta / 12.0);
        }

        double draw() {
            oldValue = low + delta * generateRN();
            return oldValue;
        }

        double draw(double value_for_low, double value_for_high) {
            low = value_for_low;
            high = value_for_high;
            delta = high - low;
            mean = 0.5 * (low + high);
            stdDev = Math.sqrt(delta * delta / 12.0);
            // FV the following was a call to draw() which caused a stack overflow when called by superclasses.
            return (oldValue = low + delta * generateRN());
        }

        double GetLow() {
            return low;
        }

        double GetHigh() {
            return high;
        }

        void setValues(double value_for_low, double value_for_high) {
            low = value_for_low;
            high = value_for_high;
            delta = high - low;
            mean = 0.5 * (low + high);
            stdDev = Math.sqrt(delta * delta / 12.0);
        }
    };

    public static class Bernoulli extends RandomNumber // default is 50/50 split
    {

        double prob;

        Bernoulli(int strm) {
            super(strm);
            name = "Bernoulli";
            prob = 0.5;
            mean = 0.5;
            stdDev = 0.5;
        }

        Bernoulli(double probabilyOfSucesss, int strm) {
            super(strm);
            name = "Bernoulli";
            prob = probabilyOfSucesss;
            if (prob > 1.0)
                prob = 1.0;
            if (prob < 0.0)
                prob = 0.0;
            mean = prob;
            stdDev = Math.sqrt(prob * (1.0 - prob));
        }

        boolean draw() {
            if (generateRN() < prob) {
                oldValue = 1.0;
                return true;
            } else {
                oldValue = 0.0;
                return false;
            }
        }

        boolean draw(double probabilyOfSucesss) {
            prob = probabilyOfSucesss;
            mean = prob;
            stdDev = Math.sqrt(prob * (1.0 - prob));
            return draw();
        }

        double GetProbSuccess() {
            return prob;
        }
    };

    public static class Exponential extends RandomNumber {

        Exponential(int strm) {
            super(strm);
            name = "Exponential";
            mean = 0.0;
        }

        Exponential(double value_for_mean, int strm) {
            super(strm);
            name = "Exponential";
            mean = value_for_mean;
            stdDev = mean;
            if (mean < 0.0)
                mean = 0.0;
        }

        double draw() {
            oldValue = -Math.log(generateRN()) * mean;
            return oldValue;
        }

        double draw(double value_for_mean) {
            mean = value_for_mean;
            stdDev = mean;
            if (mean < 0.0)
                mean = 0.0;
            return draw();
        }
    };

    public static class Normal extends RandomNumber {
        Normal(int strm) {
            super(strm);
            name = "Normal";
            mean = 0.0;
            stdDev = 1.0;
        }

        Normal(double value_for_mean, double value_for_stdDev, int strm) {
            super(strm);
            name = "Normal";
            mean = value_for_mean;
            stdDev = value_for_stdDev;
        }

        double draw() {
            double y, z;
            if (stdDev < EPSILON)
                return mean;

            if (generateRN() < 0.5) {
                if (oldNumber > EPS1)
                    z = 0.0;
                else if (oldNumber < EPSILON)
                    z = -5.0;
                else {
                    y = Math.sqrt(-2.0 * Math.log(oldNumber));
                    z = -y + ((((y * P4 + P3) * y + P2) * y + P1) * y + P0)
                            / ((((y * Q4 + Q3) * y + Q2) * y + Q1) * y + Q0);
                }
            } else {
                if (oldNumber < EPS2)
                    z = 0.0;
                else if (oldNumber > EPS3)
                    z = 5.0;
                else {
                    y = Math.sqrt(-2.0 * Math.log(1.0 - oldNumber));
                    z = y - ((((y * P4 + P3) * y + P2) * y + P1) * y + P0)
                            / ((((y * Q4 + Q3) * y + Q2) * y + Q1) * y + Q0);
                }
            }

            oldValue = mean + z * stdDev;
            return oldValue;
        }

        double draw(double value_for_mean, double value_for_stdDev) {
            mean = value_for_mean;
            stdDev = value_for_stdDev;
            return draw();
        }

        void setValues(double value_for_mean, double value_for_stdDev) {
            mean = value_for_mean;
            stdDev = value_for_stdDev;
        }
    };

    public static class Gamma extends RandomNumber {

        double shape, scale;

        Gamma(int strm) {
            super(strm);
            name = "Gamma";
            mean = shape = scale = 0.0;
        }

        Gamma(double value_for_mean, double value_for_stdDev, int strm) {
            super(strm);
            name = "Gamma";
            mean = value_for_mean;
            stdDev = value_for_stdDev;
            if (mean < EPSILON) {
                mean = shape = scale = 0.0;
                return;
            }
            if (stdDev < EPSILON) {
                stdDev = 0.0;
                oldValue = mean;
                scale = mean;
                shape = 0.0;
                return;
            }
            scale = stdDev * stdDev / mean;
            shape = mean * mean / (stdDev * stdDev);
        }

        double draw() {
            int k;
            double a, b, q, d, u1, u2, y, w, v, z;

            if (mean < EPSILON)
                return 0.0;
            if (stdDev < EPSILON)
                return mean;

            // first test if erlang
            k = (int) (shape + 0.5);
            if ((k > 0) && (Math.abs(shape - k) < 0.01)) {
                q = mean / k;
                oldValue = 0.0;
                for (int i = 0; i < k; ++i)
                    oldValue += q * (-Math.log(generateRN()));
                return oldValue;
            }

            // not erlang
            if (shape > 1.0) {
                a = 1.0 / Math.sqrt(2.0 * shape - 1.0);
                b = shape - 1.386294361; //     ' log(4) = 1.386294361   
                q = shape + 1.0 / a;
                d = 2.504077397; //             ' 1+log(4.5) = 2.504077397 
                for (int i = 0; i < 500; ++i) {
                    u1 = generateRN();
                    u2 = generateRN();
                    v = a * Math.log(u1 / (1.0 - u1));
                    y = shape * Math.exp(v);
                    z = u1 * u1 * u2;
                    w = b + q * v - y;
                    oldValue = scale * y;
                    if ((w + d - 4.5 * z) >= 0)
                        return oldValue;
                    if (w >= Math.log(z))
                        return oldValue;
                }
                throw new KlabException("gamma random generator: error generating gamma variate");
            }

            b = (2.718281828 + shape) / 2.718281828; // ' e = 2.7818281828
            for (int i = 0; i < 500; ++i) {
                u1 = generateRN();
                u2 = generateRN();
                q = b * u1;
                if (q > 1) {
                    y = -Math.log((b - q) / shape);
                    if (u2 <= Math.pow(y, (shape - 1))) {
                        oldValue = scale * y;
                        return oldValue;
                    }
                } else { //  ' now q <= 1
                    y = Math.pow(q, (1.0 / shape));
                    if (u2 <= Math.exp(-y)) {
                        oldValue = scale * y;
                        return oldValue;
                    }
                }
            }

            throw new KlabException("gamma random generator: error generating gamma variate");
        }

        double draw(double value_for_mean, double value_for_stdDev) {

            mean = value_for_mean;
            stdDev = value_for_stdDev;
            if (mean < EPSILON) {
                mean = shape = scale = 0.0;
                return 0.0;
            }
            if (stdDev < EPSILON) {
                stdDev = 0.0;
                oldValue = mean;
                scale = mean;
                shape = 0.0;
                return mean;
            }
            scale = stdDev * stdDev / mean;
            shape = mean * mean / (stdDev * stdDev);
            return draw();
        }

        double GetShape() {
            return shape;
        }

        void PutShape(double value_for_shape) {
            shape = value_for_shape;
            if (shape < EPSILON) {
                scale = stdDev = shape = mean = 0.0;
                return;
            }
            mean = scale * shape;
            stdDev = scale * Math.sqrt(shape);
        }

        double GetScale() {
            return scale;
        }

        void PutScale(double value_for_scale) {
            scale = value_for_scale;
            if (scale < EPSILON) {
                scale = stdDev = shape = mean = 0.0;
                return;
            }
            mean = scale * shape;
            stdDev = scale * Math.sqrt(shape);
        }

        void setValues(double value_for_mean, double value_for_stdDev) {

            mean = value_for_mean;
            stdDev = value_for_stdDev;
            if (mean < EPSILON) {
                mean = shape = scale = 0.0;
                return;
            }
            if (stdDev < EPSILON) {
                stdDev = 0.0;
                oldValue = mean;
                scale = mean;
                shape = 0.0;
                return;
            }
            scale = stdDev * stdDev / mean;
            shape = mean * mean / (stdDev * stdDev);
        }
    };

    public static class GamNorm {
        double mean, stdDev;
        boolean useNorm;
        Gamma gamVar;
        Normal normVar;
        String name;
        double oldValue;

        GamNorm(int strm) {
            name = "GamNorm";
            gamVar = new Gamma(strm);
            normVar = new Normal(strm);
            mean = stdDev = 0.0;
            useNorm = true;
        }

        GamNorm(double value_for_mean, double value_for_stdDev, int strm) {
            name = "GamNorm";
            gamVar = new Gamma(value_for_mean, value_for_stdDev, strm);
            normVar = new Normal(value_for_mean, value_for_stdDev, strm);
            mean = value_for_mean;
            if (mean < EPSILON)
                mean = 0.0;
            stdDev = value_for_stdDev;
            if (stdDev < EPSILON) {
                oldValue = mean;
                stdDev = 0.0;
            }
            useNorm = (mean >= (5.01 * stdDev));
        }

        double draw() {
            if (stdDev < EPSILON)
                return mean;

            if (useNorm)
                oldValue = normVar.draw();
            else
                oldValue = gamVar.draw();
            return oldValue;
        }

        double draw(double value_for_mean, double value_for_stdDev) {
            mean = value_for_mean;
            stdDev = value_for_stdDev;
            gamVar.setValues(value_for_mean, value_for_stdDev);
            normVar.setValues(value_for_mean, value_for_stdDev);
            if (mean < EPSILON) {
                mean = 0.0;
                oldValue = mean;
                return mean;
            }
            if (stdDev < EPSILON) {
                oldValue = mean;
                stdDev = 0.0;
                return mean;
            }

            useNorm = (mean >= (5.01 * stdDev));
            if (useNorm) {
                oldValue = normVar.draw(mean, stdDev);
            } else {
                oldValue = gamVar.draw(mean, stdDev);
            }
            return oldValue;
        }

        double GetMean() {
            return mean;
        }

        double GetStdDev() {
            return stdDev;
        }

        double GetRV() {
            return oldValue;
        }

        void setValues(double value_for_mean, double value_for_stdDev) {
            mean = value_for_mean;
            stdDev = value_for_stdDev;
            gamVar.setValues(value_for_mean, value_for_stdDev);
            normVar.setValues(value_for_mean, value_for_stdDev);
            if (mean < EPSILON) {
                mean = 0.0;
                return;
            }
            if (stdDev < EPSILON) {
                stdDev = 0.0;
            }
            useNorm = (mean >= (5.01 * stdDev));
        }
    };

    public static class LogNormal extends Normal {
        double logMean;
        double logStdDev;
        double oldLogValue;

        private void FixMeanStDev() {
            double mu2, sigma2;
            if (logMean < EPSILON) {
                logMean = 0.0;
                return;
            }
            if (logStdDev < EPSILON) {
                logStdDev = 0.0;
                return;
            }
            mu2 = logMean * logMean;
            sigma2 = logStdDev * logStdDev;
            mean = 0.5 * Math.log((mu2 * mu2) / (sigma2 + mu2)); //  ' mean of normal
            stdDev = Math.sqrt(Math.log((mu2 + sigma2) / mu2)); //        ' stddev of normal
        }

        LogNormal(int strm) {
            super(strm);
            name = "LogNormal"; // default is lognormal of standardized normal
            mean = 0.0; // mean of normal
            stdDev = 1.0; // for normal
            logMean = 1.64872127; // value of sqrt(e)
            logStdDev = 2.161197416; // value of sqrt((e-1)*e)
        }

        LogNormal(double value_for_mean, double value_for_stdDev, int strm) {
            super(strm);
            name = "LogNormal";
            logMean = value_for_mean;
            logStdDev = value_for_stdDev;
            FixMeanStDev();
        }

        double draw() {

            if (logMean < EPSILON) {
                oldLogValue = 0.0;
                return 0.0;
            }
            if (logStdDev < EPSILON) {
                oldLogValue = logMean;
                return logMean;
            }
            oldLogValue = Math.exp(super.draw());
            return oldLogValue;
        }

        double draw(double value_for_mean, double value_for_stdDev) {
            logMean = value_for_mean;
            logStdDev = value_for_stdDev;
            FixMeanStDev();
            return draw();
        }

        double getRV() {
            return oldLogValue;
        }

        double getMean() {
            return logMean;
        }

        double getStdDev() {
            return logStdDev;
        }
    };

    public static class CorrelatedLogNormal extends Normal
    // variable 1 is always generated first, then variable 2 is generated
    // based on the value for variable 1
    {
        double logMean1;
        double logStdDev1;
        double logMean2;
        double logStdDev2;
        double oldLogValue;
        double oldLogValue2;
        double logRho; // ' correlation coefficient
        double m1, m2, s1, reducedS2;
        double rFactor; // ' equals rho*s2/s1

        private void FixMeanStDev() {
            double mu2, sigma2, s2, r;
            double coefVar1, coefVar2;
            if ((logMean1 < EPSILON) || (logMean2 < EPSILON)) {
                logMean1 = 0.0;
                logMean2 = 0.0;
                return;
            }
            if ((logStdDev1 < EPSILON) || (logStdDev2 < EPSILON)) {
                logStdDev1 = 0.0;
                logStdDev2 = 0.0;
                return;
            }
            if (logRho > 1.0)
                logRho = 1.0;
            if (logRho < -1.0)
                logRho = -1.0;

            coefVar1 = logStdDev1 / logMean1;
            coefVar2 = logStdDev2 / logMean2;

            mu2 = logMean1 * logMean1;
            sigma2 = logStdDev1 * logStdDev1;
            m1 = 0.5 * Math.log((mu2 * mu2) / (sigma2 + mu2)); //  ' mean of normal
            s1 = Math.sqrt(Math.log(1 + coefVar1 * coefVar1)); //   ' stddev of normal

            mu2 = logMean2 * logMean2;
            sigma2 = logStdDev2 * logStdDev2;
            m2 = 0.5 * Math.log((mu2 * mu2) / (sigma2 + mu2)); //  ' mean of normal
            s2 = Math.sqrt(Math.log(1.0 + coefVar2 * coefVar2)); //       ' stddev of normal

            r = Math.log(1.0 + logRho * coefVar1 * coefVar2) / (s1 * s2);
            rFactor = r * s2 / s1;
            reducedS2 = s2 * Math.sqrt(1.0 - r * r);
            //  note stdDevValue is fixed, but meanValue varies each time
        }

        CorrelatedLogNormal(int strm) {
            super(strm);
            name = "CorrelatedLogNormal";
            m1 = 0.0; // mean of normal
            s1 = 1.0; // for normal
            logMean1 = 1.64872127; // value of sqrt(e)
            logStdDev1 = 2.161197416; // value of sqrt((e-1)*e)
            m2 = 0.0; // mean of normal
            reducedS2 = 1.0; // for normal
            logMean2 = 1.64872127; // value of sqrt(e)
            logStdDev2 = 2.161197416; // value of sqrt((e-1)*e)
            logRho = 0.0;
        }

        CorrelatedLogNormal(double value_for_mean1, double value_for_stdDev1, double value_for_mean2,
                double value_for_stdDev2, double value_for_correlation_coef, int strm) {
            super(strm);
            name = "CorrelatedLogNormal";
            logMean1 = value_for_mean1;
            logStdDev1 = value_for_stdDev1;
            logMean2 = value_for_mean2;
            logStdDev2 = value_for_stdDev2;
            logRho = value_for_correlation_coef;
            FixMeanStDev();
        }

        double draw() {
            if (logMean1 < EPSILON) {
                oldLogValue = 0.0;
                return 0.0;
            }
            if (logStdDev1 < EPSILON) {
                oldLogValue = logMean1;
                return logMean1;
            }
            mean = m1;
            stdDev = s1;
            oldLogValue = Math.exp(super.draw());
            return oldLogValue;
        }

        double draw(double value_for_mean1, double value_for_stdDev1, double value_for_mean2,
                double value_for_stdDev2, double value_for_correlation_coef) {

            logMean1 = value_for_mean1;
            logStdDev1 = value_for_stdDev1;
            logMean2 = value_for_mean2;
            logStdDev2 = value_for_stdDev2;
            logRho = value_for_correlation_coef;
            FixMeanStDev();
            if (logMean1 < EPSILON) {
                oldLogValue = 0.0;
                return 0.0;
            }
            if (logStdDev1 < EPSILON) {
                oldLogValue = logMean1;
                return logMean1;
            }
            mean = m1;
            stdDev = s1;
            oldLogValue = Math.exp(super.draw());
            return oldLogValue;
        }

        //  generates according the second mean and standard deviation
        //  correlated with the random variate previously generated by draw()
        double Generate2() {

            if (logMean2 < EPSILON) {
                oldLogValue2 = 0.0;
                return 0.0;
            }
            if (logStdDev2 < EPSILON) {
                oldLogValue2 = logMean2;
                return logMean2;
            }
            mean = m2 + rFactor * (oldValue - m1);
            stdDev = reducedS2;
            oldLogValue2 = Math.exp(super.draw());
            return oldLogValue2;
        }

        double Generate2(double value_for_first_logValue) {

            if (logMean2 < EPSILON) {
                oldLogValue2 = 0.0;
                return 0.0;
            }
            if (logStdDev2 < EPSILON) {
                oldLogValue2 = logMean2;
                return logMean2;
            }
            mean = m2 + rFactor * (Math.log(value_for_first_logValue) - m1);
            stdDev = reducedS2;
            oldLogValue2 = Math.exp(super.draw());
            return oldLogValue2;
        }

        double getRV() {
            return oldLogValue;
        }

        double GetRV2() {
            return oldLogValue2;
        }

        double getMean() {
            return logMean1;
        }

        double getStdDev() {
            return logStdDev1;
        }

        double GetMean2() {
            return logMean2;
        }

        double GetStdDev2() {
            return logStdDev2;
        }

        double GetCorrelation() {
            return logRho;
        }
    };

    //  ' =====================================================================================
    //  ' 
    //  '    SamplStat Class , purpose is to use in SampleStat Class
    //        The SamplStat Class does not avg across replications
    //  '
    //  ' =====================================================================================
    public static class SamplStat
    // ' uses Welford's algorithm to avoid round-off error
    // ' of squared terms if data values are large
    {
        String name = "SamplStat";
        double ssq, minVal, maxVal, meanVal;
        long num;

        SamplStat() {
            minVal = 1.0E+300;
            maxVal = -1.0E+300;
            ssq = meanVal = 0.0;
            num = 0;
        }

        SamplStat(String statName) {
            this();
            name = statName;
        }

        double mean() {
            return meanVal;
        }

        double stdDev() {
            if (num < 2)
                return 0.0;
            return Math.sqrt(ssq / (num - 1));
        }

        double mina() {
            return minVal;
        }

        double maxa() {
            return maxVal;
        }

        long sampleSize() {
            return num;
        }

        void AddValue(double x) {

            double d;
            ++num;
            d = x - meanVal;
            meanVal += d / num;
            ssq += d * d * (num - 1.0) / num;
            if (x < minVal)
                minVal = x;
            if (x > maxVal)
                maxVal = x;
        }

        void Reset() {

            meanVal = 0.0;
            ssq = 0.0;
            minVal = 1.0E+300;
            maxVal = -1.0E+300;
            num = 0;

        }
    };

    // '  used for 90% confidence intervals, (5% each tail)
    // '  not accurate for d.f.=1, marginally accurate for d.f. = 2 or 3, okay for d.f.>3
    //   a return of -1 indicates and error
    static double StudentT(double degreesOfFreedom) {
        double df, rc = 0.0;
        if (degreesOfFreedom < 0.99)
            return -1.0;

        //'  the following are for a 95% (2.5% each tail) C.I.
        //       'rc = 1.96 + 2.372384 / degreesOfFreedom
        //       'df = degreesOfFreedom * degreesOfFreedom
        //       'rc += 2.822707 / df
        //       'df *= degreesOfFreedom
        //       'rc += 2.55611 / df
        //       'df *= degreesOfFreedom
        //       'rc += 1.589749 / df
        //       'Return rc

        //       ''''''''''''''''''' numbers for 90% C.I.
        rc = 1.645 + 1.52411 / degreesOfFreedom;
        df = degreesOfFreedom * degreesOfFreedom;
        rc += 1.42068 / df;
        df *= degreesOfFreedom;
        rc += 0.98347 / df;
        df *= degreesOfFreedom;
        rc += 0.43417 / df;
        return rc;

    }

    //'  right-hand tail used for 90% confidence intervals, (5% each tail)
    //'  not accurate for d.f.=1, marginally accurate for d.f. = 2 or 3, okay for d.f.>3
    static double ChiSquaredRight(double degreesOfFreedom) {

        double fac, rc;
        if (degreesOfFreedom < 0.99)
            return -1.0;

        fac = 2.0 / (9.0 * degreesOfFreedom);
        rc = 1.0 - fac + 1.645 * Math.sqrt(fac);
        return degreesOfFreedom * rc * rc * rc;

    }

    //'  left-hand tail used for 90% confidence intervals, (5% each tail)
    //'  not accurate for d.f.<4, marginally accurate for d.f. < 10

    static double ChiSquaredLeft(double degreesOfFreedom) {

        double fac, rc;
        if (degreesOfFreedom < 0.99)
            return -1.0;

        fac = 2.0 / (9.0 * degreesOfFreedom);
        rc = 1.0 - fac - 1.645 * Math.sqrt(fac);
        return degreesOfFreedom * rc * rc * rc;

    }

    public static class SemiEmpirical extends Uniform {

        double[] bounds;
        int[] heights;
        int totalHeights;
        int intervals;
        double[] cumProbabilities;

        SemiEmpirical(int strm) {
            super(strm);
            name = "SemiEmpirical";
            intervals = 1;
            bounds = new double[2];
            bounds[0] = 0.0;
            bounds[1] = 1.0;
            heights = new int[1];
            heights[0] = 1;
            totalHeights = 1;
            cumProbabilities = new double[1];
            cumProbabilities[0] = heights[0] / (double) totalHeights;
        }

        // takes in two arrays corresponding to bounds and heights for the distribution
        SemiEmpirical(double values_for_bounds[], int values_for_heights[], int value_for_intervals, int strm) {
            super(strm);
            name = "SemiEmpirical";
            intervals = value_for_intervals;
            bounds = new double[intervals + 1];
            heights = new int[intervals];
            cumProbabilities = new double[intervals];
            totalHeights = 0;
            for (int i = 0; i < intervals; i++) {
                bounds[i] = values_for_bounds[i];
                heights[i] = values_for_heights[i];
                totalHeights += heights[i];
            }
            bounds[intervals] = values_for_bounds[intervals];

            cumProbabilities[0] = heights[0] / (double) totalHeights;
            for (int i = 1; i < intervals; i++) {
                cumProbabilities[i] = heights[i] / (double) totalHeights + cumProbabilities[i - 1];
            }
        }

        double draw() {
            double rand = generateRN();
            int i;

            for (i = 0; i < intervals; i++) {
                if (cumProbabilities[i] >= rand)
                    break;
            }

            return super.draw(bounds[i], bounds[i + 1]);

        }

        double draw(double value_for_given) {

            double rand = generateRN();
            double complement = 0.0;
            double holdCum = 0.0;
            int startIndx;
            if (value_for_given < bounds[1])
                return draw();
            if (value_for_given > bounds[intervals])
                return value_for_given;

            for (startIndx = 1; startIndx < intervals; startIndx++) {
                if ((complement < EPSILON) && (value_for_given < bounds[startIndx + 1])) {
                    holdCum = cumProbabilities[startIndx - 1];
                    complement = 1.0 - holdCum;
                }
                if (complement > EPSILON) {
                    if (((cumProbabilities[startIndx] - holdCum) / complement) >= rand)
                        break;
                }
            }

            if (complement < EPSILON)
                return 0.0;

            holdCum = bounds[startIndx];
            if (value_for_given > holdCum)
                holdCum = value_for_given;

            double val = super.draw(holdCum, bounds[startIndx + 1]);

            return val;

        }

        double draw(double[] values_for_bounds, int[] values_for_heights, int value_for_intervals) {
            setValues(values_for_bounds, values_for_heights, value_for_intervals);
            return draw();

        }

        double draw(double[] values_for_bounds, int[] values_for_heights, int value_for_intervals,
                double value_for_given) {
            setValues(values_for_bounds, values_for_heights, value_for_intervals);
            return draw(value_for_given);
        }

        void setValues(double[] values_for_bounds, int[] values_for_heights, int value_for_intervals) {

            intervals = value_for_intervals;
            bounds = new double[intervals + 1];
            heights = new int[intervals];
            cumProbabilities = new double[intervals];
            totalHeights = 0;
            for (int i = 0; i < intervals; i++) {
                bounds[i] = values_for_bounds[i];
                heights[i] = values_for_heights[i];
                totalHeights += heights[i];
            }
            bounds[intervals] = values_for_bounds[intervals];

            cumProbabilities[0] = heights[0] / (double) totalHeights;
            for (int i = 1; i < intervals; i++) {
                cumProbabilities[i] = heights[i] / (double) totalHeights + cumProbabilities[i - 1];
            }
        }
    };

}
