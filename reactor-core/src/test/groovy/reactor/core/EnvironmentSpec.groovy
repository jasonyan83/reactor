package reactor.core

import java.lang.management.ManagementFactory
import java.lang.management.ThreadMXBean

import reactor.core.configuration.ConfigurationReader;
import reactor.core.configuration.ReactorConfiguration
import reactor.fn.dispatch.Dispatcher
import spock.lang.Specification

class EnvironmentSpec extends Specification {

	def "An environment cleans up its Dispatchers when it's shut down"() {

		given: "An Environment"

		ReactorConfiguration configuration = new ReactorConfiguration([], 'default', [:] as Properties)
		Dispatcher dispatcher = Mock(Dispatcher)
		Environment environment = new Environment(['alpha':[dispatcher, dispatcher], 'bravo':[dispatcher]], Mock(ConfigurationReader, {
			read() >> configuration
		}))

		when: "it is shut down"
		environment.shutdown()

		then: "its dispatchers are cleaned up"
		3 * dispatcher.shutdown()
	}
}
