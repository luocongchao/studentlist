

export const tokenKey = "Admin-Token"
export const global = {
    baseurl: "http://localhost:8086",
    objExtendEx: function (obj1, obj2) {
          for(var k in obj2){
              if(!obj2.hasOwnProperty(k)) continue;
              if(obj1.hasOwnProperty(k)){
                  console.log("拓展对象失败，目标对象已存在该对象"+k);
                  continue;
              }
              obj1[k]=obj2[k];
          }
    }
}



