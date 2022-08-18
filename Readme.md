# myCodi
## 1. 프로젝트 소개
착장 자랑 공유 사이트 

#### Front-end developer
- **심채운**
- **김경문**
#### Back-end developer
- **김재영**
  * 댓글 CRUD, 좋아요
- **전태훈**
  * 회원 관리
- **정윤혁**
  * 게시글 CRUD,S3

## 2. 기간
- 2022.08.12 ~ 2022.08.18

## 3. 와이어 프레임

![](wireFrame.png)
## 4. ERD
![](table.png)![](class.png)


## 5. 주요 기능
-  Image Upload
-  LogIn & SignIn
-  Post CRUD
-  Comment CRUD


## 6. Trouble Shooting
### FE

### BE (코드 첨부 필요)
- API 명세서를 짰으나, 프론트와 같이 API 명세서를 만들어야하는데 그렇지 못해서  API 명세서 수정 후 프론트와 공유하지 않아서 오류 발생하여 req,res를 수정하는 절차가 많았다.
- CORS 이슈: corsconfig를 만들어서 sop를 해결했지만, 이후 header가 노출이 안되는 문제가 나와서 addExposedHeader()를 사용.




## 7. Git Commit Message Rule

### **Format: [ 수정자 ] < type > commit message**

- **feat** : 새로운 기능에 대한 커밋
- **fix** : 버그 수정에 대한 커밋
- **build** : 빌드 관련 파일 수정에 대한 커밋
- **chore** : 그 외 자잘한 수정에 대한 커밋
- **ci** : CI관련 설정 수정에 대한 커밋
- **cd** : CD관련 설정 수정에 대한 커밋
- **docs** : 문서 수정에 대한 커밋
- **style** : 코드 스타일 혹은 포맷 등에 관 한 커밋
- **refactor** : 코드 리팩토링에 대한 커밋
- **test** : 테스트 코드 수정에 대한 커밋