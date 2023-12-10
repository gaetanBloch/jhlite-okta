# OAuth 2.0 and OpenID Connect

OAuth is a stateful security mechanism, like HTTP Session. Spring Security provides excellent OAuth 2.0 and OIDC support, and this is leveraged by JHipster. If you’re not sure what OAuth and OpenID Connect (OIDC) are, please see [What the Heck is OAuth](https://developer.okta.com/blog/2017/06/21/what-the-heck-is-oauth)?

## Okta

If you’d like to use Okta instead of Keycloak, it’s pretty quick using the Okta CLI. After you’ve installed it, run:

```
okta register
```

Then, in your JHipster app’s directory, run `okta apps create jhipster`. This will set up an Okta app for you, create `ROLE_ADMIN` and `ROLE_USER` groups, create a `.okta.env` file with your Okta settings, and configure a `groups` claim in your ID token.

Run `source .okta.env` and start your app with Maven or Gradle. You should be able to sign in with the credentials you registered with.

If you’re on Windows, you should install [WSL](https://docs.microsoft.com/en-us/windows/wsl/install-win10) so the `source` command will work.

If you’d like to configure things manually through the Okta Admin Console, see the instructions below.

###  Create an OIDC App with the Okta Admin Console

First, you’ll need to create a free developer account at [https://developer.okta.com/signup](https://developer.okta.com/signup). After doing so, you’ll get your own Okta domain, that has a name like `https://dev-123456.okta.com`.

Update `src/main/resources/config/application-okta.yml` to use your Okta settings. Hint: replace {yourOktaDomain} with your org’s name (e.g., dev-123456.okta.com).

```properties
security.oauth2.client.provider.oidc.issuer-uri=https://{yourOktaDomain}/oauth2/default
security.oauth2.client.registration.oidc.client-id={client-id}
security.oauth2.client.registration.oidc.scope: openid,profile,email
```

Modify `okta.sh` to use your Okta settings.

```bash
export SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_OIDC_CLIENT_SECRET={client-secret}
```

Create an OIDC App in Okta to get a `{client-id}` and `{client-secret}`. To do this, log in to your Okta Developer account and navigate to **Applications > Applications > Add Application > Create New App**. Select **Web, OpenID Connect** and click **Create**. Give the app a name you’ll remember, and specify http://localhost:8080/login/oauth2/code/oidc and http://localhost:8080/swagger-ui/oauth2-redirect.html as Login redirect URIs. Add http://localhost:8080 as a Logout redirect URI and click Save. Copy the client ID into your `application-okta.yml` file and the client secret into your `okta.sh` file.

Create a `ROLE_ADMIN` and `ROLE_USER` group (**Directory > Groups > Add Group**) and add users to them. You can use the account you signed up with, or create a new user (**Directory > People > Add Person**). Navigate to **Security > API > Authorization Servers**, and click on the `default` server. Click the **Claims** tab and **Add Claim**. Name it `groups`, and include it in the ID Token. Set the value type to `Groups` and set the filter to be a Regex of `.*`. Click **Create**.

![security-add-claim.png](images/security-add-claim.png)

After making these changes, you should be good to go! If you have any issues, please post them to [Stack Overflow](https://stackoverflow.com/questions/tagged/jhipster). Make sure to tag your question with “jhipster” and “okta”.

To use Okta when running e2e tests, you can set environment variables.

```bash
export CYPRESS_E2E_USERNAME=<your-username>
export CYPRESS_E2E_PASSWORD=<your-password>
```

### Running locally

In order to run your application with OAuth 2.0 and Okta:

```bash
source okta.sh
SPRING_PROFILES_ACTIVE=okta ./mvnw
```

or

```bash
source okta.sh
./mvnw -Dspring-boot.run.profiles=okta
```
