package com.bac.applicationaccount;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.annotation.Resource;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContextHSQL.xml" })
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractHibernateApplicationTestCase {


	@Resource(name = "applicationAccount")
	ApplicationAccount dao;
	
	/*
	 * Convenience method to generate a mock callback handler
	 * 
	 */
    CallbackHandler getCallbackHandler(final String callbackUser, final char[] callbackPassword)
            throws IOException, UnsupportedCallbackException {

        CallbackHandler callbackHandler = mock(CallbackHandler.class);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Callback[] callbacks = (Callback[]) args[0];
                NameCallback nameCallback = (NameCallback) callbacks[0];
                PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];
                nameCallback.setName(callbackUser);
                passwordCallback.setPassword(callbackPassword);
                return null;
            }
        }).when(callbackHandler).handle((Callback[]) anyObject());
        return callbackHandler;
    }
}
