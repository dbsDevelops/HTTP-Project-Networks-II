function setCookie(key, value) {
    var expires = new Date();

    //Expires in 1 year
    expires.setTime(expires.getTime() + (365 * 24 * 60 * 60 * 1000));
    document.cookie = key + '=' + value + ';expires=' + expires.toUTCString();
}

function getCookie(key) {
    var keyValue = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');

    return keyValue ? keyValue[2] : null;
}

function deleteAllCookies() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
}

function saveAllCookies() {
    setCookie('score', score);
    setCookie('autoclicker', autoclicker.clicksPerSecondBase);
    setCookie('gladiatorUnloked', BoolToInt(GladiatorUnloked));
    setCookie('hatUnloked', BoolToInt(HatUnloked));
    setCookie('demonUnloked',  BoolToInt(DemonUnloked));
    setCookie('archeologistUnloked',  BoolToInt(ArcheologistUnloked));
    setCookie('suitUnloked',  BoolToInt(SuitUnloked));
    setCookie('robotUnloked',  BoolToInt(RobotUnloked));
    setCookie('cubeUnloked',  BoolToInt(CubeUnloked));
    setCookie('clankUnloked',  BoolToInt(ClankUnloked));
    setCookie('sparkUnloked',  BoolToInt(SparkUnloked));
}

function loadAllCookies() {
    if (getCookie('score') != null) score = Number(getCookie('score'));
    if(getCookie('autoclicker') != null) autoclicker.clicksPerSecondBase = Number(getCookie('autoclicker'));
    if(getCookie('gladiatorUnloked') != null) GladiatorUnloked = IntToBool(getCookie('gladiatorUnloked'));
    if(getCookie('hatUnloked') != null) HatUnloked = IntToBool(getCookie('hatUnloked'));
    if(getCookie('demonUnloked') != null) DemonUnloked = IntToBool(getCookie('demonUnloked'));
    if(getCookie('archeologistUnloked') != null) ArcheologistUnloked = IntToBool(getCookie('archeologistUnloked'));
    if(getCookie('suitUnloked') != null) SuitUnloked = IntToBool(getCookie('suitUnloked'));
    if(getCookie('robotUnloked') != null) RobotUnloked = IntToBool(getCookie('robotUnloked'));
    if(getCookie('cubeUnloked') != null) CubeUnloked = IntToBool(getCookie('cubeUnloked'));
    if(getCookie('clankUnloked') != null) ClankUnloked = IntToBool(getCookie('clankUnloked'));
    if(getCookie('sparkUnloked') != null) SparkUnloked = IntToBool(getCookie('sparkUnloked'));
}

function BoolToInt(bool) {
    if (bool) return 1;
    else return 0;
}

function IntToBool(int) {
    if (int == 1) return true;
    else return false;
}