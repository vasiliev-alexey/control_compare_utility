package com.av.utils;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexey
 * Date: 5/24/14
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextDiffUtils {


    public static String getDiffStr(List<String> original, List<String> revised) {


        Patch patch = DiffUtils.diff(original, revised);
        L2B l2B = new L2B();

        StringBuilder tl = new StringBuilder();
        StringBuilder tr = new StringBuilder();
        List<Delta> deltas = patch.getDeltas();
        //   String[] cors = new String[]{"palegreen", "khaki", "pink", "moccasin", "lightskyblue", "lightyellow", "coral", "aliceblue", "yellowgreen", "beige", "lightpink"};

        int cori = 0;
        int last = 0;
        for (Delta delta : deltas) {
         /*   if (last + 1 < delta.getOriginal().getPosition()) { // not continuous
             //   tl.append("<pre style='font-size:smaller;'>\n");
              //  tr.append("<pre style='font-size:smaller;'>\n");
                for (int i = last + 1; i < delta.getOriginal().getPosition(); i++) {
                    //   tl.append(original.get(i) + "\n");
                    //  tr.append(original.get(i) + "\n");
                }
                // tl.append("</pre>\n");
                // tr.append("</pre>\n");
            }*/
            List<?> or = delta.getOriginal().getLines();
            //   System.out.println("<pre style='color:"+cors[cori]+";'>\n"+l2B.l2b(or)+"\n</pre>");
            tl.append("<pre font color=red>\n" + l2B.l2b(or) + "\n</font></pre>");
            List<?> re = delta.getRevised().getLines();
            //    System.out.println("<pre style='color:"+cors[cori]+";'>\n"+l2B.l2b(re)+"\n</pre>");
            tr.append("<pre font color=red>\n" + l2B.l2b(re) + "\n</font></pre>");
            //    cori = (cori < cors.length) ? cori + 1 : 0;
            last = delta.getOriginal().last();
        }
       /* if (last + 1 < original.size()) { //last is not delta
            //  tl.append("<pre style='font-size:smaller;'>\n");
            //  tr.append("<pre style='font-size:smaller;'>\n");
            for (int i = last + 1; i < original.size(); i++) {
                //    tl.append(original.get(i) + "\n");
                //    tr.append(original.get(i) + "\n");
            }
            //    tl.append("</pre>\n");
            //   tr.append("</pre>\n");
        }*/
        //enabled_flag != null ? !enabled_flag.equals(control.enabled_flag) : control.enabled_flag != null
        String left = tl.toString();
        left = (left != null || left.length() >0) ? left : "NULL";
        String right = tr.toString();
        right = (right != null || right.length()> 0) ? right : "NULL";

        //return "<html><table><tr><td style='vertical-align:bottom;'>" + tl.toString() + "</td><td style='vertical-align:bottom;'>" + tr.toString() + "</td></tr></table></html>";
        return "<html><table><tr><td style='vertical-align:bottom;'>" + left + "</td><td style='vertical-align:bottom;'>" + right + "</td></tr></table></html>";

    }

    private static class L2B {
        private StringBuilder sb = new StringBuilder();

        StringBuilder l2b(List<?> l) {
            sb.delete(0, sb.length());
            for (Object object : l) {
                sb.append(object + "\n");
            }
            if (sb.length() >= 1) sb.deleteCharAt(sb.length() - 1); // last "\n"
            return sb;
        }
    }
}
