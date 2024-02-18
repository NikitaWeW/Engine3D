/*
see CollisionDetection.txt
*/
package Engine;

import org.joml.Vector3f;
import org.joml.Vector2f;

public final class CollisionDetection {
    private CollisionDetection() {}
    /*
     * for more information about collision detection check CollisionDetection.txt
     */

    private static float sqr(float a) { return (float) a*a; }
    private static float round(float value) { return (float) Math.round(value*10000)/10000; }
    
    private static float areaOfTheTriangle(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Cx, float Cy, float Cz) {
        float a = distance3D(Ax, Ay, Az, Bx, By, Bz);
        float b = distance3D(Bx, By, Bz, Cx, Cy, Cz);
        float c = distance3D(Cx, Cy, Cz, Ax, Ay, Az);

        float s = (a + b + c) / 2;

        return (float) Math.sqrt((double) s*(s - a)*(s - b)*(s - c));
    }

    public static float magnitude3D(float Ax, float Ay, float Az) { return (float) Math.sqrt((double) sqr(Ax) + sqr(Ay) + sqr(Az)); }
    public static float magnitude2D(float Ax, float Ay) { return (float) Math.sqrt((double) sqr(Ax) + sqr(Ay)); }

    public static float dot3D(float Ax, float Ay, float Az, float Bx, float By, float Bz) { return Ax * Bx + Ay * By + Az * Bz; }
    public static float dot2D(float Ax, float Ay, float Bx, float By) { return Ax * Bx + Ay * By; }

    public static float cross3D(float Ax, float Ay, float Az, float Bx, float By, float Bz) { return  magnitude3D(Ax, Ay, Az) * magnitude3D(Bx, By, Bz) * (float) Math.sin((double) angleBetweenVectors3D(Ax, Ay, Az, Bx, By, Bz)); }
    public static float cross2D(float Ax, float Ay, float Bx, float By) { return  magnitude2D(Ax, Ay) * magnitude2D(Bx, By) * (float) Math.sin((double) angleBetweenVectors2D(Ax, Ay, Bx, By)); }

    public static float angleBetweenVectors3D(float Ax, float Ay, float Az, float Bx, float By, float Bz) {
        if(Ax == 0 && Ay == 0 && Az == 0 || Bx == 0 && By == 0 && Bz == 0) return Float.NaN; //its imposible to calculate the angle between vectors when one of these vectors is (0, 0, 0)
        return (float) Math.toDegrees(Math.acos((double) round(dot3D(Ax, Ay, Az, Bx, By, Bz) / (magnitude3D(Ax, Ay, Az) * magnitude3D(Bx, By, Bz)))));
    }
    public static float angleBetweenVectors2D(float Ax, float Ay, float Bx, float By) {
        if(Ax == 0 && Ay == 0 || Bx == 0 && By == 0) return Float.NaN; //its imposible to calculate the angle between vectors when one of these vectors is (0, 0)
        return (float) Math.toDegrees(Math.acos((double) round(dot2D(Ax, Ay, Bx, By) / (magnitude2D(Ax, Ay) * magnitude2D(Bx, By)))));
    }
    
    public static float distance3D(float Ax, float Ay, float Az, float Bx, float By, float Bz) { return (float) Math.sqrt((double) sqr(Ax-Bx)+sqr(Ay-By)+sqr(Az-Bz)); }
    public static float distance2D(float Ax, float Ay, float Bx, float By) { return (float) Math.sqrt((double) sqr(Ax-Bx)+sqr(Ay-By)); }
    
    //2D

    //returns min value and then max value
    public static Vector2f[] findAABB2D(Vector2f[] vertices) {
        Vector2f max = new Vector2f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        Vector2f min = new Vector2f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);

        for(Vector2f vertex : vertices) {
            if(vertex.x > max.x) max.x = vertex.x;
            if(vertex.y > max.y) max.y = vertex.y;

            if(vertex.x < min.x) min.x = vertex.x;
            if(vertex.y < min.y) min.y = vertex.y;
        }

        return new Vector2f[] {min, max};
    }
    public static float[] findAABB2D(float[] vertices) {
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;

        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;

        for(int i = 0; i < vertices.length; i+=2) {
            if(vertices[i] > maxX) maxX = vertices[i];
            if(vertices[i+1] > maxY) maxY = vertices[i+1];

            if(vertices[i]   < minX) minX = vertices[i];
            if(vertices[i+1] < minY) minY = vertices[i+1];
        }

        return new float[] {minX, minY, maxX, maxY};
    }

    public static boolean checkAABBColision2D(float minX, float minY, float maxX, float maxY, float anotherMinX, float anotherMinY, float anotherMaxX, float anotherMaxY) {
        if (minX <= anotherMaxX &&
            minY <= anotherMaxY &&
            maxX >= anotherMinX &&
            maxY >= anotherMinY) return true;
        return false;
    }
    public static boolean checkAABBColision2D(Vector2f min, Vector2f max, Vector2f anotherMin, Vector2f anotherMax) {
        return checkAABBColision2D(min.x, min.y, max.x, max.y, anotherMin.x, anotherMin.y, anotherMax.x, anotherMax.y);
    }

    public static boolean pointBelongsLine2D(float Ax, float Ay, float Bx, float By, float Px, float Py) {
        float angle = angleBetweenVectors2D(Bx - Ax, By - Ay, Px - Ax, Py - Ay);
        if(Float.isNaN(angle)) return true; //point A or point P is equal to point B so B - A = 0 or P - A = 0 and when its 0 angle is NaN
        return angle == 0 || angle == 180;
    }
    public static boolean pointBelongsLine2D(Vector3f A, Vector3f B, Vector3f P) {
        return pointBelongsLine2D(A.x, A.y, B.x, B.y, P.x, P.y);
    }

    public static float[] lineLineIntersection2D(float Ax, float Ay, float Bx, float By, float Px, float Py, float Qx, float Qy) {
        if(pointBelongsLine2D(Ax, Ay, Bx, By, Px, Py) && pointBelongsLine2D(Ax, Ay, Bx, By, Qx, Qy)) return new float[] {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY}; //same lines

        float denominator = (Ax - Bx) * (Py - Qy) - (Ay - By) * (Px - Qx);
        if(denominator == 0) return null; //no intersection points

        float Dx = ( (Ax * By - Ay * Bx) * (Px - Qx) - (Ax - Bx) * (Px * Qy - Py * Qx) ) / denominator;
        float Dy = ( (Ax*By - Ay*Bx)*(Py - Qy) - (Ay - By)*(Px*Qy - Py*Qx) ) / denominator;

        return new float[] {Dx, Dy};
    }
    public static Vector2f lineLineIntersection2D(Vector2f A, Vector2f B, Vector2f P, Vector2f Q) {
        float[] result = lineLineIntersection2D(A.x, A.y, B.x, B.y, P.x, P.y, Q.x, Q.y);
        return new Vector2f(result[0], result[1]);
    }
    
    public static boolean pointBelongsSegment2D(float Ax, float Ay, float Bx, float By, float Px, float Py) {
        return round(distance2D(Ax, Ay, Bx, By)) == round(distance2D(Ax, Ay, Px, Py) + distance2D(Px, Py, Bx, By));
    }
    public static boolean pointBelongsSegment2D(Vector3f A, Vector3f B, Vector3f P) {
        return round(A.distance(B)) == round(A.distance(P) + B.distance(P));
    }

    public static boolean pointBelongsRay2D(float Ax, float Ay, float Bx, float By, float Px, float Py) {
        float angle = angleBetweenVectors2D(Px - Ax, Py - Ay, Bx - Ax, By - Ay);
        if(Float.isNaN(angle)) return true; //point A or point P is equal to point B so B - A = 0 or P - A = 0 and when its 0 angle is NaN
        return angle == 0;
    }
    public static boolean pointBelongsRay2D(Vector2f A, Vector2f B, Vector2f P) {
        return pointBelongsRay2D(A.x, A.y, B.x, B.y, P.x, P.y);
    }

    public static float[] segmentSegmentIntersection2D(float Ax, float Ay, float Bx, float By, float Px, float Py, float Qx, float Qy) {
        float[] intersectionPoint = lineLineIntersection2D(Ax, Ay, Bx, By, Px, Py, Qx, Qy);
        if(intersectionPoint != null 
            && pointBelongsSegment2D(Ax, Ay, Bx, By, intersectionPoint[0], intersectionPoint[1]) 
            && pointBelongsSegment2D(Px, Py, Qx, Qy, intersectionPoint[0], intersectionPoint[1])) return intersectionPoint;
        return null;
    }
    public static Vector2f segmentSegmentIntersection2D(Vector2f A, Vector2f B, Vector2f P, Vector2f Q) {
        float[] result = segmentSegmentIntersection2D(A.x, A.y, B.x, B.y, P.x, P.y, Q.x, Q.y);
        return new Vector2f(result[0], result[1]);
    }

    //3D

    //returns min value and then max value
    public static Vector3f[] findAABB3D(Vector3f[] vertices) {
        Vector3f max = new Vector3f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        Vector3f min = new Vector3f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);

        for(Vector3f vertex : vertices) {
            if(vertex.x > max.x) max.x = vertex.x;
            if(vertex.y > max.y) max.y = vertex.y;
            if(vertex.z > max.z) max.z = vertex.z;

            if(vertex.x < min.x) min.x = vertex.x;
            if(vertex.y < min.y) min.y = vertex.y;
            if(vertex.z < min.z) min.z = vertex.z;
        }

        return new Vector3f[] {min, max};
    }
    public static float[] findAABB3D(float[] vertices) {
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        float maxZ = Float.NEGATIVE_INFINITY;

        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float minZ = Float.POSITIVE_INFINITY;

        for(int i = 0; i < vertices.length; i+=3) {
            if(vertices[i] > maxX) maxX = vertices[i];
            if(vertices[i+1] > maxY) maxY = vertices[i+1];
            if(vertices[i+2] > maxZ) maxZ = vertices[i+2];

            if(vertices[i]   < minX) minX = vertices[i];
            if(vertices[i+1] < minY) minY = vertices[i+1];
            if(vertices[i+2] < minZ) minZ = vertices[i+2];
        }

        return new float[] {minX, minY, minZ, maxX, maxY, maxZ};
    }

    public static boolean checkAABBColision3D(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float anotherMinX, float anotherMinY, float anotherMinZ, float anotherMaxX, float anotherMaxY, float anotherMaxZ) {
        if (minX <= anotherMaxX &&
            minY <= anotherMaxY &&
            minZ <= anotherMaxZ &&
            maxX >= anotherMinX &&
            maxY >= anotherMinY &&
            maxZ >= anotherMinZ) return true;
        return false;
    }
    public static boolean checkAABBColision3D(Vector3f min, Vector3f max, Vector3f anotherMin, Vector3f anotherMax) {
        return checkAABBColision3D(min.x, min.y, min.z, max.x, max.y, max.z, anotherMin.x, anotherMin.y, anotherMin.z, anotherMax.x, anotherMax.y, anotherMax.z);
    }

    public static boolean pointBelongsLine3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Px, float Py, float Pz) {
        float angle = angleBetweenVectors3D(Bx - Ax, By - Ay, Bz - Az, Px - Ax, Py - Ay, Pz - Az);
        if(Float.isNaN(angle)) return true; //point A or point P is equal to point B so B - A = 0 or P - A = 0 and when its 0 angle is NaN
        return angle == 0 || angle == 180;
    }
    public static boolean pointBelongsLine3D(Vector3f A, Vector3f B, Vector3f P) {
        return pointBelongsLine3D(A.x, A.y, A.z, B.x, B.y, B.z, P.x, P.y, P.z);
    }
    
    public static boolean pointBelongsSegment3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Px, float Py, float Pz) {
        return round(distance3D(Ax, Ay, Az, Bx, By, Bz)) == round(distance3D(Ax, Ay, Az, Px, Py, Pz) + distance3D(Px, Py, Pz, Bx, By, Bz));
    }
    public static boolean pointBelongsSegment3D(Vector3f A, Vector3f B, Vector3f P) {
        return round(A.distance(B)) == round(A.distance(P) + B.distance(P));
    }

    public static boolean pointBelongsRay3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Px, float Py, float Pz) {
        float angle = angleBetweenVectors3D(Px - Ax, Py - Ay, Pz - Az, Bx - Ax, By - Ay, Bz - Az);
        if(Float.isNaN(angle)) return true; //point A or point P is equal to point B so B - A = 0 or P - A = 0 and when its 0 angle is NaN
        return angle == 0;
    }
    public static boolean pointBelongsRay3D(Vector3f A, Vector3f B, Vector3f P) {
        return pointBelongsRay3D(A.x, A.y, A.z, B.x, B.y, B.z, P.x, P.y, P.z);
    }

    public static boolean pointBelongsTriangle3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Cx, float Cy, float Cz, float Px, float Py, float Pz) {
        return round(areaOfTheTriangle(Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz)) ==
            round(areaOfTheTriangle(Ax, Ay, Az, Bx, By, Bz, Px, Py, Pz) + 
            areaOfTheTriangle(Px, Py, Pz, Bx, By, Bz, Cx, Cy, Cz) + 
            areaOfTheTriangle(Ax, Ay, Az, Px, Py, Pz, Cx, Cy, Cz));
    }
    public static boolean pointBelongsTriangle3D(Vector3f A, Vector3f B, Vector3f C, Vector3f P) {
        return pointBelongsTriangle3D(A.x, A.y, A.z, B.x, B.y, B.z, C.x, C.y, C.z, P.x, P.y, P.z);
    }

    public static float[] segmentSegmentIntersection3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Px, float Py, float Pz, float Qx, float Qy, float Qz) {
        float[] intersectionPoint = lineLineIntersection3D(Ax, Ay, Az, Bx, By, Bz, Px, Py, Pz, Qx, Qy, Qz);
        if(intersectionPoint[0] == Float.POSITIVE_INFINITY) intersectionPoint[0] = Ax;
        if(intersectionPoint[1] == Float.POSITIVE_INFINITY) intersectionPoint[1] = Ay;
        if(intersectionPoint[2] == Float.POSITIVE_INFINITY) intersectionPoint[2] = Az;
        if(intersectionPoint != null 
            && pointBelongsSegment3D(Ax, Ay, Az, Bx, By, Bz, intersectionPoint[0], intersectionPoint[1], intersectionPoint[2]) 
            && pointBelongsSegment3D(Px, Py, Pz, Qx, Qy, Qz, intersectionPoint[0], intersectionPoint[1], intersectionPoint[2])) return intersectionPoint;
        return null;
    }
    public static Vector3f segmentSegmentIntersection3D(Vector3f A, Vector3f B, Vector3f P, Vector3f Q) {
        float[] result = segmentSegmentIntersection3D(A.x, A.y, A.z, B.x, B.y, B.z, P.x, P.y, P.z, Q.x, Q.y, Q.z);
        return new Vector3f(result[0], result[1], result[2]);
    }

    public static float[] lineLineIntersection3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Px, float Py, float Pz, float Qx, float Qy, float Qz) {
        float[] xyIntersection = lineLineIntersection2D(Ax, Ay, Bx, By, Px, Py, Qx, Qy); //project lines on 2D planes
        float[] yzIntersection = lineLineIntersection2D(Ay, Az, By, Bz, Py, Pz, Qy, Qz);
        float[] zxIntersection = lineLineIntersection2D(Az, Ax, Bz, Bx, Pz, Px, Qz, Qx);

        if(xyIntersection == null || yzIntersection == null || zxIntersection == null) return null; //no intersection points

        if(xyIntersection[0] == Float.POSITIVE_INFINITY) xyIntersection = new float[] {zxIntersection[1], yzIntersection[0]}; //handle infinity amount of intersection points
        if(yzIntersection[0] == Float.POSITIVE_INFINITY) yzIntersection = new float[] {xyIntersection[1], zxIntersection[0]};
        if(zxIntersection[0] == Float.POSITIVE_INFINITY) zxIntersection = new float[] {yzIntersection[1], xyIntersection[0]};

        if(xyIntersection[0] == zxIntersection[1] && yzIntersection[0] == xyIntersection[1] && zxIntersection[0] == yzIntersection[1]) return new float[] {
            xyIntersection[0],
            yzIntersection[0],
            zxIntersection[0]
        };

        return null;
    }
    public static float[] lineLineIntersection3D(Vector3f A, Vector3f B, Vector3f P, Vector3f Q) {
        return lineLineIntersection3D(A.x, A.y, A.z, B.x, B.y, B.z, P.x, P.y, P.z, Q.x, Q.y, P.z);
    }

    public static Vector3f linePlaneIntersection3D(Vector3f A, Vector3f B, Vector3f C, Vector3f P, Vector3f Q) {
        float[] result = linePlaneIntersection3D(A.x, A.y, A.z, B.x, B.y, B.z, C.x, C.y, C.z, P.x, P.y, P.z, Q.x, Q.y, Q.z);
        return new Vector3f(result[0], result[1], result[2]);
    }
    public static float[] linePlaneIntersection3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Cx, float Cy, float Cz, float Px, float Py, float Pz, float Qx, float Qy, float Qz) {
        float numerator = -(Px-Ax)*(By-Ay)*(Cz-Az) - //solve the equation
                           (Py-Ay)*(Bz-Az)*(Cx-Ax) -
                           (Pz-Az)*(Bx-Ax)*(Cy-Ay) +
                           (Pz-Az)*(By-Ay)*(Cx-Ax) +
                           (Px-Ax)*(Bz-Az)*(Cy-Ay) +
                           (Py-Ay)*(Bx-Ax)*(Cz-Az);

        float denominator = (Qx-Px)*(By-Ay)*(Cz-Az) +
                            (Qy-Py)*(Bz-Az)*(Cx-Ax) +
                            (Qz-Pz)*(Bx-Ax)*(Cy-Ay) -
                            (Qz-Pz)*(By-Ay)*(Cx-Ax) -
                            (Qx-Px)*(Bz-Az)*(Cy-Ay) -
                            (Qy-Py)*(Bx-Ax)*(Cz-Az);

        if(numerator == 0 && denominator == 0) return new float[]{Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY}; //infinity amount of intersection points
        if(denominator == 0) return null; //no intersection points

        float t = numerator / denominator;
        
        return new float[]{t * (Qx - Px) + Px, t * (Qy - Py) + Py, t * (Qz - Pz) + Pz}; //intersection point
    }

    public static Vector3f triLineIntersection3D(Vector3f A, Vector3f B, Vector3f C, Vector3f P, Vector3f Q) {
        float[] result = triLineIntersection3D(A.x, A.y, A.z, B.x, B.y, B.z, C.x, C.y, C.z, P.x, P.y, P.z, Q.x, Q.y, Q.z);
        if(result != null) return new Vector3f(result[0], result[1], result[2]);
        return null;
    }
    public static float[] triLineIntersection3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Cx, float Cy, float Cz, float Px, float Py, float Pz, float Qx, float Qy, float Qz) {
        float[] result = linePlaneIntersection3D(Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz, Px, Py, Pz, Qx, Qy, Qz);
        if(result == null || result[0] == Float.POSITIVE_INFINITY) return result;
        if(pointBelongsTriangle3D(Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz, result[0], result[1], result[2])) return result;
        return null;
    }

    public static boolean triSegIntersection3D(Vector3f A, Vector3f B, Vector3f C, Vector3f P, Vector3f Q) {
        return triSegIntersection3D(A.x, A.y, A.z, B.x, B.y, B.z, C.x, C.y, C.z, P.x, P.y, P.z, Q.x, Q.y, Q.z);
    }
    public static boolean triSegIntersection3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Cx, float Cy, float Cz, float Px, float Py, float Pz, float Qx, float Qy, float Qz) {
        float[] abcAABB = findAABB3D(new float[] {Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz});
        float[] pqAABB = findAABB3D(new float[] {Px, Py, Pz, Qx, Qy, Qz});
        if(!checkAABBColision3D(abcAABB[0], abcAABB[1], abcAABB[2], abcAABB[3], abcAABB[4], abcAABB[5], pqAABB[0], pqAABB[1], pqAABB[2], pqAABB[3], pqAABB[4], pqAABB[5])) return false;

        float[] D = linePlaneIntersection3D(Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz, Px, Py, Pz, Qx, Qy, Qz);

        if(D[0] == Float.POSITIVE_INFINITY) { //it becomes 2D problem
            float[] result = segmentSegmentIntersection3D(Ax, Ay, Az, Bx, By, Bz, Px, Py, Pz, Qx, Qy, Qz);
            if(result != null) return true;
            result = segmentSegmentIntersection3D(Ax, Ay, Az, Cx, Cy, Cz, Px, Py, Pz, Qx, Qy, Qz);
            if(result != null) return true;
            result = segmentSegmentIntersection3D(Cx, Cy, Cz, Bx, By, Bz, Px, Py, Pz, Qx, Qy, Qz);
            if(result != null) return true;
        };

        float Dx = D[0];
        float Dy = D[1];
        float Dz = D[2];

        return pointBelongsSegment3D(Px, Py, Pz, Qx, Qy, Qz, Dx, Dy, Dz) && pointBelongsTriangle3D(Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz, Dx, Dy, Dz);
    }

    public static boolean triRayIntersection3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Cx, float Cy, float Cz, float Px, float Py, float Pz, float Qx, float Qy, float Qz) {
        float[] result = triLineIntersection3D(Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz, Px, Py, Pz, Qx, Qy, Qz);
        return result != null && pointBelongsRay3D(Px, Py, Pz, Qx, Qy, Qz, result[0], result[1], result[2]);
    }
    public static boolean triRayIntersection3D(Vector3f A, Vector3f B, Vector3f C, Vector3f P, Vector3f Q) {
        return triRayIntersection3D(A.x, A.y, A.z, B.x, B.y, B.z, C.x, C.y, C.z, P.x, P.y, P.z, Q.x, Q.y, Q.z);
    }

    public static boolean triTriIntersection3D(Vector3f A, Vector3f B, Vector3f C, Vector3f P, Vector3f Q, Vector3f R) {
        return triTriIntersection3D(A.x, A.y, A.z, B.x, B.y, B.z, C.x, C.y, C.z, P.x, P.y, P.z, Q.x, Q.y, Q.z, R.x, R.y, R.z);
    }
    public static boolean triTriIntersection3D(float Ax, float Ay, float Az, float Bx, float By, float Bz, float Cx, float Cy, float Cz, float Px, float Py, float Pz, float Qx, float Qy, float Qz, float Rx, float Ry, float Rz) {
        //Axis Aligned Bounding Box for faster result
        float[] ABCAABB = findAABB3D(new float[] {Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz});
        float[] PQRAABB = findAABB3D(new float[] {Px, Py, Pz, Qx, Qy, Qz, Rx, Ry, Rz});
        if(!checkAABBColision3D(ABCAABB[0], ABCAABB[1], ABCAABB[2], ABCAABB[3], ABCAABB[4], ABCAABB[5], 
                                PQRAABB[0], PQRAABB[1], PQRAABB[2], PQRAABB[3], PQRAABB[4], PQRAABB[5])) return false; //no intersection

        if(triSegIntersection3D(Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz, Px, Py, Pz, Qx, Qy, Qz)) return true;
        if(triSegIntersection3D(Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz, Qx, Qy, Qz, Rx, Ry, Rz)) return true;
        if(triSegIntersection3D(Ax, Ay, Az, Bx, By, Bz, Cx, Cy, Cz, Rx, Ry, Rz, Px, Py, Pz)) return true;

        return false;
    }
}
