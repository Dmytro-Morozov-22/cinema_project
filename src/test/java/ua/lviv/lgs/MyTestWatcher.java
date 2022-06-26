package ua.lviv.lgs;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class MyTestWatcher implements TestWatcher {

	@Override
	public void testAborted(ExtensionContext extensionContext, Throwable throwable) {
		System.out.println("ABORTED --> " + extensionContext.getDisplayName());
	}

	@Override
	public void testDisabled(ExtensionContext extensionContext, Optional<String> optional) {
		System.out.println("DISABLED --> " + extensionContext.getDisplayName());
	}

	@Override
	public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
		System.out.println("FAILED --> " + extensionContext.getDisplayName());
	}

	@Override
	public void testSuccessful(ExtensionContext extensionContext) {
		System.out
				.println("SUCCEEDED --> " + extensionContext.getDisplayName());
	}
}
