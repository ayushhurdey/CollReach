//const URL_ORIGIN = location.origin.replace(/.{0,5}$/, '');
const URL_ORIGIN =  location.protocol + "//" + location.hostname;
const GATEWAY_URL = URL_ORIGIN + ":8085";

const POSTS_URL = GATEWAY_URL + "/posts";
const USER_PROFILE_URL = GATEWAY_URL + "/userprofile";
const CHAT_URL = GATEWAY_URL + "/chat";
const FRONTEND_URL = GATEWAY_URL;

// const POSTS_URL = URL_ORIGIN + ":8084";
// const USER_PROFILE_URL = URL_ORIGIN + ":8082";
// const CHAT_URL = URL_ORIGIN + ":8083";
// const FRONTEND_URL = URL_ORIGIN + ":8081";

