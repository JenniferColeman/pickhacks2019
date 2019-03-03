package com.pickhacks.pickhacks2019;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {

    static private String url = "http://dffedf11.ngrok.io" + "/HelloWorld.html?diet=ketogenic.html";
    static private ArrayList<SearchItem> mSearchList;
    static private RequestQueue requestQueue;

    static RecyclerView mRecyclerView;
    static RecyclerView.LayoutManager mLayoutManager;
    static RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String mCurrentDiet = mSharedPrefs.getString("currentDiet", "Not Available");
        requestQueue = Volley.newRequestQueue(this);
        System.out.println("CURRENT DIET: " + mCurrentDiet);

        try {

            getSearchItemList(mCurrentDiet);
            // add to the list
            String basic_img_base64 = "iVBORw0KGgoAAAANSUhEUgAAAgAAAAIACAMAAADDpiTIAAAAA3NCSVQICAjb4U/gAAAACXBIWXMAAAuUAAALlAF37bb0AAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAAwBQTFRF////AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACyO34QAAAP90Uk5TAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDEyMzQ1Njc4OTo7PD0+P0BBQkNERUZHSElKS0xNTk9QUVJTVFVWV1hZWltcXV5fYGFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6e3x9fn" +
                    "+AgYKDhIWGh4iJiouMjY6PkJGSk5SVlpeYmZqbnJ2en6ChoqOkpaanqKmqq6ytrq+wsbKztLW2t7i5uru8vb6/wMHCw8TFxsfIycrLzM3Oz9DR0tPU1dbX2Nna29zd3t/g4eLj5OXm5+jp6uvs7e7v8PHy8/T19vf4+fr7/P3+6wjZNQAAITlJREFUGBntwQmAjeX+B/DvmX0MMxj7MlKS9ZZoI9qVtFB/qqvVmtBCudpTqOSWujepKC2iUtlvJa12KYmyj33JOsOMmTMz5/tPq2Xm/J53Oe975n2fzwfQNE3TNE3TNE3TNE3TNE3TolvZk9tcd/fT40YPvefm1knQ/CShzWNfZ/MI+XOebAXNF2LPHPRpDosx53JoXhfb/v39LNH3/4DmZScN3cqwcm6E5lXJN34RougFaJ502qj9VNIPmvc0m0pVh5pC85h/fBSiuuVJ0LykyfshGvIMNO9oODFEg/YmQPOIE8cX0biroHlC4iOHaMYEaF7QdjXNySkLrdSr+R5Naw+tlIvrf4Dm9YZWurVaRiuGQivN0l8L0ZI3cLzK1wx47r15m9b97/k+l5SFFsUu2kqLZuNoyW2Hfx/i3/Y+kQYtSsU/HaJVP+MIZbt9lsdjrWsGLSrV/5bW/YS/nPnKARYntwm0KNTtIG2wEL+reOcylmRZIrRoU+F92mIWDqv94iGGMRxalDlvE+3xIYA6o/MZ1u44aNEkbmgRbfIG6r4apORiaFGk8te0zdTXCigbBS16/COTTvsCWtTocICOWw4tWjwYovN+gRYdkifQDUFoUaHmYrpiO7RocOY2uuN7aFGgyyG6ZCY09w2hCfv20gZjobktMIrG7X4wdQ1t0A+ay+LepmE77yuLQB5t0AKauxIn06itdycDqE4b5MZDc1XKZzRo8x2JOOwc2uBraK6qMJ/GFI5Iwe+upw2GQXNT1R9ozOJm+NO/aIMW0FyUsZqGZN8Zg7+MonXrobmo/iYaMrkWjjCT1j0NzT2n7qQRWzrgKCtoXXNormm8mwYUvVAORztIy9ZCc029bTRg4zk4RjqtuwuaW2pvoAEzKuJYzWnZnhRoLqm6iuoK7w/gONfQsieguaTCD1S37TwU4x5adagKNHeUXUB1s6uiOM/TqpeguSPpCyoLPRGDYk2mRUX1oLkifgaV7boUJfieFr0PzRWBd6hsbi2UZC8tOgOaKx6nshFxKEkqLfoCmitupKqiHihZU1rUDpobzs2jorxrEcYVtGYZNDecuIuKsi9EOH1oTWdoLij/" +
                    "MxX90hxhPUNLPofmgrjPqGhjfYT3Hq0oaAzNBa9Q0YqaECykFf+G5oIBVLSgIiQ7aMG2ctCcd0Eh1XycAkkyregCzXnVdlDNhHiITqEFX0NzXuyXVPPfGMja0rzCptCcN4xqHoOKHjTveWjOuzxEJfdCyVCatiMNmuMy9lDJYKh5m6bdAs1x8Quo5Fko+oZmzQ1Ac9zzVPIqVG3i8QqpoPA0aI67mkomxEBRfBGPlffCAioYAc1x6TuoYmocVNXlMYrG1RlJBd8nQHPcRKqYnQRl5/NoUxqjNxXkNoTmuE5UMT8F6m7lkb5qCbQtoILe0BxXZRcVrE2HAY/ybz9cDqBxFhVMg+a8D6hgXwMY8Rr/tL5LAECVTCrYXhma426ggoKLYchs/m5H33j8KmkeFYQug+a4anuooBeMWcfDsh5OwWGBCVQxEprzplDBczAmJkgy79lK+N1gqliWCM1xV1LB9BgYU5Msej0Df+hCFYeaQHNc4lrKfigLg1pyciP86bw8qugHzXkPULY7A0Y1Owd/aZFNFTOhOa/mQYpCl8OKRrupYmdVaM57h7KnYUXdLVTSHprzWlM2Jw4WVF9LJU9Cc17sUop214IFFX+kkmkx0JzXm6LQ5bCg7AIqWVEOmvOSd1L0FCxInE0le06C5oK7KZoXB/PiJlNJwUXQXJC0jZJD9WFe4C2quROaG/pSdC8s+C/VjIHmhoTNlMyPgXlDqGZOAjQ33E7JoQYw7xGq2VgFmhviN1IyEOY9SDU5zaC5ojslC2Nh2v1U1AmaO1ZQUNQUpl1IRU9Ac8e5lLwB8+ZRzUcBaO54i4K8OjCtPdX8WBaaOyoeouA5mBb4nkq214XmkrspyKoE07pRyd6m0NyygoKHYVpGFlUcPBuaW86lYEcKTJtFFfmXQHPNGxT0gWm3U0XhtdBcE7uH4a2Nh1l1D1BFN2juaUXBDTAr8AVVDIDmomEM77sAzHqYKoZCc9NShncpzOpGFaOgqUqu3vCcdjf0HjSoz80dLz6rUa04WFeL4S2EWVcVUsE7MdBE5Vr1eXXxzjweI7hy8tNdW6bDip4Mry9MapVLBTPioYVV64qH3l8TYjjb3739FJg1lWEVVIY5jfdSwdfJ0EpWpuO4nVSzbXz3ujDhrCKGNRPmZGyhgu/SoJWkardph2jIoruqwqC4pQyvC0xJ/5kKVlWGVrxTBs4tonGFH99UFkYMYHg5KTCjzHwq2JwBrTgnPbWSpuW8cRqUZRxkeO/AjLgZVHEptGJcOKWI1nx2eQBqplDQHiYE3qCKKdCOk9RtGW3wU/ckKOhAwa54mDCcKvJOgnaMmkN30SY7esdBkryJgtEwLvAMlQyFdrSz3gnSRqs6QtCdkk4wLO51KtmSAu1I/" +
                    "7eAdptzDsL6gZLqMCppKtV0gHaEc+YzEt6rgpK1oWQdjEr7mmpGQfvbie8xQn65BiWaRMmbMKjaUqpZlgTtT+VH5DNy3iyP4tUupKQnjDlpHdXkNoL2h/g7dzOiNl+CYg2jJDcDhrTfS0U9of2hw2pGWujFMijG95T0hhGxQ0NU9D6037X4ik5Y0xLHSQhSMA1GVJlNVT+mQjus0pshOqPwqQQcozkFOyrDgFZbqGp7BrTDrthB5yxrgqP1oKAd1AX6F1BVzhnQflVuDB2V3Q5HeYnhvQh1DedQWegaaL9qk0mHFfbBkeYyrMyyUJXwaD7V3QcNSBxRROc9H4u/zWNYV0FVq59owMvQgNNX0BXTy+IvnzGsT+OgpMKoEA2YHgct9uEgXbK0Fv40leGNhYKUB/fRiNlJ0E5ZSPdsa44/TKTgIUgS79pJQ+amwPcC/XLpppwO+N1YSrogrLhuG2nMkjT4XvqndFnRnfjNQEry26FktQZvpUHLK8H3mq6n+/rjsIYUFXRH8QJtPyqkUWuqw/euPchocC8OW0fZ0wEcJ6bVU2tp3MYM+F3g8RCjw0D8aiQVzL0igCNVvHbcLpqx7kT4XbnJjBqDAFxEJSv6dzyjRgySazZp9+AHmTRpWXX4Xb0VjCIPAPGbqKwgj5bMKQ+/a7uXlhWtmf+/d0YNG9izS497Hhk+6t3vDtK0h4FudMqMZPhd/0Jakr9kzB3nlMGxal5w+2uZNOVRxK6kM96Og88lvUkL9o/velo8Slbn1je207h70YmOeCEAn6u5iKZtHdU2HqKYi8bup1FdA0vogEfgdy2306RVT50dgKLEaz6mMYUdLwgx0oK94HfX59OUrGcbwZgzp9OQvAuHM8K2toTf9SqiGZn3pMK4FlNpRHbLxYyoL6vC7wbSjDnXxsKcZh+FqG7XlQcYQSPi4HfDaFzBhDNhwamTQlS2+SFGzIH/g98FXqRhwZG1YVGrNVS28n+MkJ8awO/i3qZhU+vDujL/CVHV8u8YEe+Vhd8lTaFRP14Ce1y4gaq+X0v77b4Rvld2Ng3a1TsWdin3KlUt2U67TawC36u4kMYE/50GO7XbSkU/HqSttl4FrfqPNGbKybBZhelUtCdE+4ReSYN2wloasv1y2C/2P3Te2gugoeEWGjIpHRFxVxGdlTMkGRqa76IRWTcjUq46SAcFX6wGDWiZRSO+rIPIOX0bnRIafxK0X52+nwbkDYhBJNX+gc6YcSq0wxrtogFLmyLC0hZQTdHSIpo2pzW035y0jeqKnk5AxKUtoJq84Zk0Je+tc6D9rlYm1W1rDSekLaCa4K2v07gNgypD+0OVlVS3sAackbaAagqu77iLhoQ+vjIG2p8q/EB1byTCKWkLqKbolvIDMqns5yfrQftbuYVUVngPHJS2mGpCPRHb8UsqKPyy" +
                    "/8nQjpT8JZXtvQSOqrSSakL9AJz62kGGlfXujRWhHS1hJpWtqAeHZWymovvwq7gzB0zezeJkf/NC12bx0I4V8z6VTSkHxzXaQ0UP43eBRre/8uFXP27L468Kd69Z9MnEYZ3qBaAV61kqGxKAC84+SEVDcLSUjDqpAWhh9aWqnE5wx2VBKroPmkFXFlHR1lPhlhtCVBPqAs2QFjlUtK4u3NOPioIXQzOgznYqWlEDbhpMRdnNoCkrv4KKFqfDXQOpaPsJ0BTFf05FX6XCbbcWUM2qdGhq3qCimclw35W5VPNFLDQVj1HRu/GIBq32Uc0IaApuoaIxMYgOTbZSTWdoonODVPMsokadtVRysDE0QY3tVPMookittVSyOg1aWAnzqSR0N6JKrbVUMiUALZzRVNMDUab2Oip5CFoY3ajmX4g6tddRReEZ0Ep0Zh6VPIsolLGeKpYnQCtBlc1U8lYA0ShjPVUMhVa8uC+pZEYcolOdTCooaA6tWCOpZF4ZRKs6mVSwLAFaMbpQyYqKiF4n7KCCx6Ed77RcqthYC9HsnDzKCk6DdqwK66lidwNEt1uo4PsYaMf4kCoOnolo9wwV3ArtaH2pItgWUS9mBmUbE6EdqVkeFYRuQCmQ+hNl/aEdoexqquiHUqFJHkW7U6H97W2qeAKlxL2UDYH2l9uo4mWUFjFfUXSwGrQ/NMyhgg9iUGqckE3RKGi/S/6RCr5IRCnSlaJgPWi/eZkKMiuiVJlC0UvQDruOCg6djtKl2gFKsspAA07MooJuKG0GU3QrNMQtooIxKHXK/ULJHGh4gAqWJKH0uYuihvC9xvmU7TkBpVBCJiX/ht/FLaasqB1KpZso2ZUAn3uACh5D6RTzAyWd4W+N8ymbGYNS6hpKZsHXYhdTllkRpVXcTgpC1eFn91N26HSUXs9SciN8rHE+Zd1QijWlZCz8K3YxZWNQqi2hIBP+dT9lS5JQqvWjpC78qnEeRXtOQOmWnk9BN/hU7CKKitqhtJtEwdvwqfspewyl3rUUbIM/Nc6jaGYMSr3qlDSAH8UuomhbOjxgIwV3wI8GUXYFvOA9Ct6BDzXKo2gsPKE/BfPgP7GLKNqYCk9oRcEm+M8gikIXwRuSggyvMBZ+0yiPov/CK76loBZ8JnYRRWvKwCv+S8HZ8JlBFBW1hGf0oaAT/KVRHkXD4R03UdAfvhK7kKLlibCsURdEh6soeBa+MpCiguaw7KL9BZciKpxPwfvwk1oHKXoMlnULkllNEA1Op2A+/GQiRUviYFHgSR62sRqiQD0K1sJHzqcorzEsSnqPv1tUBu6rQsFP8I/YZRQNhEVV5vNPH8bAdUkUfAf/uJOiuTGwpuF6/u0ZuC/I8ObBNyrvoyTnZFhz4T4eqSdct5vhzYZvjKGoH6zpGuRRCtrCZYEgw5sBvzgjRMnsAKwIDOOxsprAXdUo" +
                    "+AA+EVhISXYdWJH0Lo+3oSpc1YKC8fCJrhTdBSsqz2NxFiXDTR0oGAt/SNtJydJYWNBgPYv3QQAu6kfBf+EPIykJtYQFF+5jSYbDRU9TMAK+0KSAktdgwW1BlqwH3DOegiHwhS8o2VsZpgWGMpyCS+CaryjoAz/oTNHtMC1pIsPb3xhuWUdBa/hAmU2ULI6BWZXnUrKhKtwRyKOgPHxgKCVFZ8CsBusoW5gMV9SlYCN84KQ8Sl6CWa33UsWkANzQg4Lp8IFplOyqALMa7KWSp+GGSRQMg/e1p6grzLsgSCXd4bzYfRRcD89LWEPJ3AAsuJVKCi6G486mpBE8735KCk+FJUOpZH8jOO1RCvJi4XW1DlLyPKwJTKSSzCpw2FwKvoPnTaRkeyosSppHJQuS4ai0QgrGwevOo6gLLKu8jkreD8BJ11DSHx4Xu4ySL2CDBvuo5Ck4aTQlF8DjelMSbAQ7XBikkm5w0HoK9sbD28psp2Q47HEblRRcBMecQsk4eNwDlGwuC5sMo5L9jeCUZym5At5WcT8lnWGXwLtUklkFzii7n4KsRHjbM5QsgH2S5lPJ/CQ4oh8l4+FttQ5R0ho2qryeSt4LwAGBNZR0hLe9SskU2KrhPip5Eg64gpKDyfC0UwopKGwIe10UpJKuiLzPKHkX3jaJkldht25UErwIkdaEos7wtDMoyakB2z1JJfsaIsJepSQ3BZ72GSVDYL/Ae1SyvjIiqtIhSj6Ep11CyS/lEAFJC6hkfhIi6UGK/gkvC3xLST9ERJVMKnk3gMiJ30pJXiq8rBMla+MRGY32U8kwRM4/KXodXha3ipLOiJSLC6jkNkRK4hpKik6Bl/WkZCEipzuVBC9EhDxM0fvwsuStlJyHCHqKSvY1QESckEvR6fCygZRMQyQF3qeSdZURCZMp+hheVn4vBYWNEVHJC6hkXhLsdzllbeBlT1IyBhFWNZNKJgZgt8S1FM2Bl1XPoSC3JiKt8X4qGQq7PULZ5fCylygZisi7pIBKboW96uZStBReVq+Agl2pcEAPKgleAFtNpew6eNlESu6CI56mkr0NYKP2lK2OgYedHqJgXQIcEZhEJesqwTZJ6yjrBi/7hJJb4ZDkhVQyNxF2eZSyzQnwsAsoWRsHp1TdQCUTArDHiYcouwtetoCSW+GcxllU8gRsEfsVZeuT4WHtKFkXBwe1LaCSW2CHJ6ngMnjZXEpug6N6UknwfFjXPkTZBHjZBZSsi4OznqGSvafAqjp7KNtXFV72OSVd4bDAB1SythKsSVhIBT3hZS0pWR8HpyUvopI5ibDkeSqYE4CXzaSkG5xXbSOVvBOABf9HBcHG8LLmlKyPgwuaZFHJ4zDv5CwqGApP+4iSbnBF2wIquRlmJS2lgjVJ8LImIQoy4+GOXlSSfx5MGkMVF8PTJlDSHW4ZQSV76sOUm6niLXha" +
                    "/SIKMuPhlpgPqWRtOkxonEMFeyrD08ZR0gPuKbOYSr5JhGFlf6aKrvC0ugUUbIiHi6ptpJLxMOwdqvgyAE8bTUlPuKppFpUMhkGDqCK/ATytZj4FG+LhrksLqeQmGNKDSgbA256npCfcdjuV5LeBAdcWUcUH8LaquRRsTIDr/k0le+pD2cX5VLEqFd42nJJecF/MR1SyJh2KzjhAFTlN4G0VD1CwJQFRoMy3VPJNIpQ03E0lXeBxj1NyL2wWgBnVN1HJ21CRsZlKXoTHpe6jICsV9mq/uDzM+Ec2lTwGWeWVVLIwAR73ICXDYavAwyHOiIEZlxVSyY2QlPuWSnbVhsel7KIgWBN2KvcRfzUEpvSmkvw2CC/xcyopaguvG0DJONip/k88LHQNTHmWSvacjHBiP6Kah+F1SdspaQIbXbGfvzvQCGbETKaS1ekI4zWqmRGA1/WlZCbsE3gkxD+tLg8zUpZQydcJKNEzVJNZAV6XsImSC2CbcpN5hBkxMKP6Zip5CyX5F9XkNYfndadkMWxzys88yhCY8o9sKnkUxetORT3geYGfKekMu1yZxaOFroEp7QqppAuK06OIal6H97WnZH0s7BF4NMRjHWgEU/pQSV5rHO8BKlqaDO+bTUlf2CN1CouxOg2mPEclu+vhGIGRVLQpA953GiW7y8AWp/zMYk2PgRkxU6hkdUUcJX48Fe1qAB94k5LHYYursliCJ2BKyhIq+SoBR0j5mIqyW8AHagQpOFQFNgg8FmJJQh1hSo3NVPIm/pa+gIryLoQfPEnJaNggdSrDyG4EU049QCWP4E+1f6Kiwo7wg5S9FBSdDOsarGRYq9NgyuWFVPJP/K7hJqq6Db7Qh5IPYN3VWRRMj4Epfakk71wcdtZuqroXvhCzhpKzYVVgcIiiJ2DO81Syux6Ayw5S1ZPwhw6UfAPLJlFBqCNMiZlKJasqokuQql6GT3xNydWw7HaqyG4EU1K+o5IvB4So6r0Y+MMZlKyPgXWvUsXqNJhScwtt9mkCfGICJQNgg4T5VDE9BqacdoC2WpACn8gooCCnPOxQYztVPAFz2hfSRssrwi9GUPIy7NEqSAWhjjCnH+2TWQN+UW4/JU1gk9upIrshzHmBdtlRD75xNyWfwzavUsWqNJgSO4322H8afCM2k5KOsE3iAqqYHoApZb+nHXJawz86UbIxFvapsZ0qHoc5NbfQuqxz4SPzKfkX7NQqSAWhDjCn2UFatacFfKQlJYfSYaveVJHdEOZcUURrdjSBn0yiZCxsNoYqVqXBnDtpyaaT4Sd1Cyk5DTZLXEAV0wIw5z+0YG0d+MrzlHwD29XcThWPw5zY6TRtRXX4SvkDlHSG/" +
                    "c4NUkGoA8wpu5QmfVcJ/nIfJVviEAG9qSK7IcyptZWmzEuDv8RuouRBRMRYqliVBnOaHaQJs1PgMx0pyauCiEhcSBXTAjDnyiIaNikJfjOLkjcQITV3UMVgmHQXjRoRgN/UD1HSApFybpAKQh1g0hwaUngH/Oc5SuYjcu6giuyGMKPWJzTkQHv4T5l9lNyICBpLFavSYNwt+2nI1mbwoW6U7EtGBCUupIppARhUdQqNWVYbfrSEkhcRUTV3UMVgGNN5N435JBV+dBZFpyOyzg1SQehqGJD+Lg16NQ6+NI6SpYi0O6giuwGUXbWDxoTuhz+lH6KkHyLuNapYmQo1aeNoUO718Kl7KcmriIhLXEgVUwNQ0XYzDdrQDD4VWEvJBDig1g6qGAxZ2Zdo1Gfp8KvLKLoYTmgdpILQ1ZC0WU+jRsTCt6ZSkhmAI/pQRVYDhJX8XIgG5dwA/6pTRMkjcMhrVLEyFWGctZJGrT8VPjaMkqIMOCRxEVVMDaAkqSMLadSsivCxhJ2UfALH1NpBFY+hBDdso2HDY+FnN1DUGc5pHaSC0NUoTsPPaVjOdfC3byjZnQAH9aGKrAY4TpkngzRszT/gb00pGglHvU4VK1NxjI4badz4cvC5lyhqCkclLqKKqQEc6cQZNC6nG/yu3AFKFsFhtXZSxWP4W+Kjh2jc8kbwvT4U9YLT2hRQQegq/OmyNTTh1WRoyynJSYXj+lJFVgP8pvYHNCH7emg4j6I34YLXqWJlKoD4gQdpwrcnQQMmUnQeXJC0mCqmBHD+CpoxMgEaUDVIyRq4otZOqvj32zRjz1XQDhtI0f1wR5sCRsxntaH9ZhUlhTXgkr6MkOxeAWi/OZeiaXDNOEbErDrQ/jCOog5wTdJi2i+7F7Q/peZQsiMO7qm9k3abVQfaX3pSNBxualNAW2X3gnaEhRQ1gKv60U6z6kA7QhOK5sJl42ib7J7QjvIcRV3hsqTFtMmsDGhHSdhNyYGycFvtX2iH7J7QjtGZojFw33kFtO7TDGjH+oSicxAF7qRV2T2hHSejiJKfEBXeoDWfZkA73qMUDUBUSPqWFmT3gFaMmA2UBKsgKpR/keZ9mgGtOJdQ9BGiQeVhWTQtqwe04k2kqBPcV+O5HJr3SQa04qXnUXIgGW6rOzqP5m2/CVpJ7qJoPFx2yrgCmhccUQ5aiZZRdCVcdeq7RbRgVkNoJTuDor0JcNFZU2nFhmughTOaorFwz/mzaMWhwcnQwimTRdHFcMtlc2jJ5LrQwruZop2xcEWg47e0ZNWl0CRfUfRfuCH2n8tpyYGBCdAkJ1N2LpwX320NrRlfA5rscYo2B+C0pL6baM0PraGpWE/RCDis7H07aM3ePrHQVLSirAUcVeOJPbSm6JVK0NSMpmgtnHTm+CAtWtACmqLEvRQNgWPirptHq3beFoCm6hrKmsAh6fdvplUFI9OgqfuIouVwRpNXcmnZ542hGVAxn6KH4ICYKz" +
                    "+jdUsvh2ZIb8rqIeLK3bmG1q3rEoBmzDyKvkWknTQyi9bt6BMPzaB6lA1AZF04pYjWZT2UAs2wxygK1UYEJXVfRhscGpEOzYS1FH2DyKk5dDdtUDi2NjQzWlLWB5Fy9oQC2uGDBtDMeYmiwqqIiKSbFtAWn58JzaSEPRTNQiQ0fWEvbbGkLTTTOlLWDbZL6bqA9lh9XQCaeR9SlF8BNms+Opv22NYrDpoFFfIpmgZbpd6+hDbZN6gMNEt6UdYFNjr7tRzaJPepCtAsmkNRblnYpcKdP9IuBS/XgGbViZRNgU3avHWIdil6pz406x6hrCvsUPnelbRNwev1odlhDUVFlWFZ4OJ382mb/NEnQLNFc8q+gVXV719H" +
                    "++SOrAnNJk9RNgCWJF8/s5D2OfB0FWi2WU9ZPZgXOH9sFm20b3BFaPZpQdlymHbK0A20064HUqHZaThlQ2FOpb6LaKtt/VOg2SuTsjNgQuK1U4K01cY+SdBsdgZlWwMwrNXofbTX2u7x0Gz3DGWjYNBJj62jzX66MRZaBGyg7FIYUaHXXNptaacYaJFwFmVZCVCW1GFSHu228EpoETKCsolQVObaCQdou6/bQouUwEbKroeKlM7vHaTtij5sBS1yzqYsmAZRuRs+yKX9Dv7nJGiR9Cxln0CQduPkQ4yALYMqQIuowCbK7kA4FW6Zns9I+P7GeGgRdg5loZooUXrX/wUZCaHpF0CLvOcoW4wSVO75aQEjIvflBtAcENhM2UMoTtXeswsZGTseqQTNEa2o4DQcp3rfL4sYIcu7JkJzyEjKtuIYte76JsRI+fRSaI4JbKFsDI5Up/+8ECMl//Wm0Bx0LhVcg780HbSQkbN7SDVojnqBsmAqfpPc/qWNjKBVvZOhOStmG2Vf4Fd17piRywgKzboyAM1prangvtjWTy1nRO0bWR+aC/5DBXP2MrK+614GmhtittN1eW+eDc0l59Ftmf+" +
                    "qBM01L9JVRTPax0BzT8wOumj38BOhuep8umfBzUnQXDaKLskdezo018XupCtW31MBWhS4kC4o/OiSALSo8BIdt31IbWhRIvYXOqtgylVx0KLGRXTU6kHVoUWTl+mc3DfbQIsusbvolMW3p0GLNhfTGXteOBVaFHqFDgh9dn0itGgU2MGI2/x4XWhR6mxGWHBSuxhoUWsYI2pF/8rQotlyRk72mHOgRbcTGSkFM29Ihhbt7mZkfHt3VWilwGxGwKZhjaCVCuULaLessecHoJUSN9BeBdOvS4ZWekygnRbfWRlaaRK/n7bZMLQBtFLmItpk/5jzAtBKnedph7zJnZKglUaZtCx/6o2p0EqnprQof/rNadBKrQdpRXDmreWhlWYLaVrBx10rQivdqoVoTuGn3dOhlXo9aEbh7F6VoHnBNBpW9EXvKtC8oUwujSn6qk81aJ5xFY0IfdOvOjQvGUNlobl31YTmLTE7qGj+PbWhec7ZVLJoQAY0LxpG2bcDT4DmUcsZXuE3950IzbPqMpwDH9xSCZqX3cESbXmpXSI0j5vK4n0/uDk070s4yOPlf9InA5ovXMRj7XmrUzlofvEMj7Lm3+fFQvORH/mXorn/agjNX2rxDwc+uq0KNN/pzsOWDb8gAZofTeK+97vWhOZX97aKhaZpmqZpmqZpmqZpmqZpmuZl/w+GkUlyC9CFKgAAAABJRU5ErkJggg==";
            mSearchList.add(new SearchItem(
                    basic_img_base64,
                    "Brief",
                    true,
                    "Time",
                    "Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Create a list of the JSON Objects

        // Create from our list
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SearchItemAdapter(mSearchList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    private static void getSearchItemList(String diet) throws JSONException {


        mSearchList = new ArrayList<>();
        //SearchActivity.url //+= diet;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Check the length of our response (to see if the user has any repos)
                if (response.length() > 0) {
                    // The user does have repos, so let's loop through them all.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            // For each repo, add a new line to our repo list.
                            JSONObject jsonObj = response.getJSONObject(i);

                            String plantName = jsonObj.getString("name");
                            System.out.println("Name: " + plantName);
                            //String plantTime = jsonObj.getString("time");
                            String plantPhoto = jsonObj.getString("photo"); // Needs to be decoded
                            System.out.println("Photo: " + plantPhoto);
                            boolean plantStar = jsonObj.getBoolean("star");
                            System.out.println("Star: " + plantStar);
                            String plantBrief = jsonObj.getString("brief");
                            System.out.println("Brief: " + plantBrief);

                            // add to the list
                            mSearchList.add(new SearchItem(
                                    plantPhoto,
                                    plantBrief,
                                    plantStar,
                                    "TIME",
                                    plantName));
                        }
                        catch (JSONException e) {
                            // If there is an error then output this to the logs.
                            e.printStackTrace();

                        }

                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        },

        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // If there a HTTP error then add a note to our repo list.
                System.out.println(error.networkResponse.headers);
                System.out.println(Arrays.toString(error.networkResponse.data));
                System.out.println("Volley");
                error.printStackTrace();
            }
        });

        // Add the request we just defined to our request queue.
        requestQueue.add(request);
    }
}
