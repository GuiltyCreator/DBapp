package com.example.a7279.db.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by a7279 on 2018/2/10.
 */

public class DataBeans  {
    /**
     * status : 200
     * info : success
     * data : {"id":2,"username":"Jay","password":"123456","avatar":null,"token":"ac2f704deb121877d0895cf5bb96716981610c5f"}
     */

    private int status;
    private String info;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * username : Jay
         * password : 123456
         * avatar : null
         * token : ac2f704deb121877d0895cf5bb96716981610c5f
         */

        private int id;
        private String username;
        private String password;
        private Object avatar;
        private String token;
        private int totalCount;
        private int totalPage;
        private int curPage;
        private List<QuestionsBean> questions;
        private List<AnswersBean> answers;

        public List<AnswersBean> getAnswers() {
            return answers;
        }

        public void setAnswers(List<AnswersBean> answers) {
            this.answers = answers;
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public List<QuestionsBean> getQuestions() {
            return questions;
        }

        public void setQuestions(List<QuestionsBean> questions) {
            this.questions = questions;
        }
        public static class QuestionsBean implements Serializable {
            /**
             * id : 2
             * title : 孤独的等待
             * content : 怎么还没有人来玩啊
             * images : http://ok4qp4ux0.bkt.clouddn.com/1485064307258
             * date : 2017-12-26 16:53:04
             * exciting : 0
             * naive : 0
             * recent : null
             * answerCount : 0
             * authorId : 2
             * authorName : Jay
             * authorAvatar : http://ok4qp4ux0.bkt.clouddn.com/img-222c4cafc0af1718a6a3b45224cf5229.jpg
             * is_exciting : false
             * is_naive : false
             * is_favorite : false
             */

            private int id;
            private String title;
            private String content;
            private String images;
            private String date;
            private int exciting;
            private int naive;
            private Object recent;
            private int answerCount;
            private int authorId;
            private String authorName;
            private String authorAvatar;
            private boolean is_exciting;
            private boolean is_naive;
            private boolean is_favorite;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getExciting() {
                return exciting;
            }

            public void setExciting(int exciting) {
                this.exciting = exciting;
            }

            public int getNaive() {
                return naive;
            }

            public void setNaive(int naive) {
                this.naive = naive;
            }

            public Object getRecent() {
                return recent;
            }

            public void setRecent(Object recent) {
                this.recent = recent;
            }

            public int getAnswerCount() {
                return answerCount;
            }

            public void setAnswerCount(int answerCount) {
                this.answerCount = answerCount;
            }

            public int getAuthorId() {
                return authorId;
            }

            public void setAuthorId(int authorId) {
                this.authorId = authorId;
            }

            public String getAuthorName() {
                return authorName;
            }

            public void setAuthorName(String authorName) {
                this.authorName = authorName;
            }

            public String getAuthorAvatar() {
                return authorAvatar;
            }

            public void setAuthorAvatar(String authorAvatar) {
                this.authorAvatar = authorAvatar;
            }

            public boolean isIs_exciting() {
                return is_exciting;
            }

            public void setIs_exciting(boolean is_exciting) {
                this.is_exciting = is_exciting;
            }

            public boolean isIs_naive() {
                return is_naive;
            }

            public void setIs_naive(boolean is_naive) {
                this.is_naive = is_naive;
            }

            public boolean isIs_favorite() {
                return is_favorite;
            }

            public void setIs_favorite(boolean is_favorite) {
                this.is_favorite = is_favorite;
            }
        }


        public static class AnswersBean {
            /**
             * id : 1
             * content : 自娱自乐
             * images : http://ok4qp4ux0.bkt.clouddn.com/img-222c4cafc0af1718a6a3b45224cf5229.jpg
             * date : 2017-12-26 16:56:33
             * best : 0
             * exciting : 0
             * naive : 0
             * authorId : 2
             * authorName : Jay
             * authorAvatar : http://ok4qp4ux0.bkt.clouddn.com/img-222c4cafc0af1718a6a3b45224cf5229.jpg
             * is_exciting : false
             * is_naive : false
             */

            private int id;
            private String content;
            private String images;
            private String date;
            private int best;
            private int exciting;
            private int naive;
            private int authorId;
            private String authorName;
            private String authorAvatar;
            private boolean is_exciting;
            private boolean is_naive;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getBest() {
                return best;
            }

            public void setBest(int best) {
                this.best = best;
            }

            public int getExciting() {
                return exciting;
            }

            public void setExciting(int exciting) {
                this.exciting = exciting;
            }

            public int getNaive() {
                return naive;
            }

            public void setNaive(int naive) {
                this.naive = naive;
            }

            public int getAuthorId() {
                return authorId;
            }

            public void setAuthorId(int authorId) {
                this.authorId = authorId;
            }

            public String getAuthorName() {
                return authorName;
            }

            public void setAuthorName(String authorName) {
                this.authorName = authorName;
            }

            public String getAuthorAvatar() {
                return authorAvatar;
            }

            public void setAuthorAvatar(String authorAvatar) {
                this.authorAvatar = authorAvatar;
            }

            public boolean isIs_exciting() {
                return is_exciting;
            }

            public void setIs_exciting(boolean is_exciting) {
                this.is_exciting = is_exciting;
            }

            public boolean isIs_naive() {
                return is_naive;
            }

            public void setIs_naive(boolean is_naive) {
                this.is_naive = is_naive;
            }
        }
    }
}
